package com.example.cryptus.controller;
import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.model.Account;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.User;
import com.example.cryptus.repository.CustomerRepository;
import com.example.cryptus.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import static org.mindrot.jbcrypt.BCrypt.gensalt;
import static org.mindrot.jbcrypt.BCrypt.hashpw;


@RestController
@RequestMapping(value = "/klant")
public class CustomerController {


    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    Account customerAccount = new Account("password");
    User user;



    private CustomerService customerService;
    CustomerRepository customerRepository;
    private CustomerDaoJdbc customerDaoJdbc;
    private final Logger logger = LogManager.getLogger(CustomerDaoJdbc.class);

    @Autowired
    public CustomerController(CustomerService customerService, CustomerDaoJdbc customerDaoJdbc) {
        this.customerService = customerService;
        this.customerDaoJdbc = customerDaoJdbc;
        logger.info("New customerController");
    }


    @GetMapping(value = "/KlantLijst")
    public List<Customer> list(){
        return customerService.list();
    }

    @GetMapping(value = "/{id}")
    @ResponseBody Optional<Customer> findCustomerById(@PathVariable("id")int id){
        return  customerService.findCustomerById(id);
    }

    @GetMapping("/findByLastname")
    @ResponseBody Optional<Customer> findCustomerByName(@RequestParam("customerName")String name){
        return  customerService.findCustomerByName(name);
    }
    @DeleteMapping(value = "/delete")
    @ResponseBody public void deleteCustomer(@PathVariable("id")int id){
        customerService.delete(id);
    }



    @GetMapping("/findByUsernamePassword")
    @ResponseBody Optional<Customer> findCustomerByUsernamePassword(@RequestParam("username") String username) {
        return customerService.findCustomerByUsernamePassword(username);

    }

    @PostMapping(value = "/create")
    @ResponseBody String createCustomer(@RequestBody Customer customer) throws NoSuchAlgorithmException{
        if (customer instanceof Customer) {
            customer.setPassword(hashpw(customer.getPassword(),gensalt(12)+ customerAccount.getPEPPER()));
            customerService.storeCustomer(customer);
        }
        return "Hello " + customer.getFirstName() + " Here is a summary of your information: \n "  + customer.toString();

    }

    @PostMapping(value = "/login")
    @ResponseBody String login(@RequestBody Account account) throws NoSuchAlgorithmException {
        Optional<Customer> expectedCustomer =
                customerDaoJdbc.findCustomerByUsernamePassword(account.getGebruikersnaam());
        Customer dbCustomer = expectedCustomer.get();
        String customer = account.getWachtwoord();
        if(BCrypt.checkpw(customer, dbCustomer.getPassword())){
            return "Hello " + dbCustomer.getFirstName() + ", login successful!";
        }
        else return "Wrong username password combination";
    }





}
