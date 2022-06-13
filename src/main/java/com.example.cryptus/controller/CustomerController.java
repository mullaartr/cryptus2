package com.example.cryptus.controller;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dto.CustomerDTO;
import com.example.cryptus.dto.CustomerConvertor;
import com.example.cryptus.model.Account;
import com.example.cryptus.model.Customer;
import com.example.cryptus.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mindrot.jbcrypt.BCrypt.gensalt;
import static org.mindrot.jbcrypt.BCrypt.hashpw;


@RestController
@RequestMapping(value = "/klant")
public class CustomerController {



    Account customerAccount = new Account("password");


    private CustomerService customerService;

    CustomerConvertor customerConvertor;

    private final Logger logger = LogManager.getLogger(CustomerDaoJdbc.class);

    @Autowired
    public CustomerController(CustomerService customerService, CustomerConvertor customerConvertor) {



            this.customerService = customerService;
            this.customerConvertor = customerConvertor;
            logger.info("New customerController");
        }


    @GetMapping
    public Customer getCurrentUser(){
        var username =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // username
        // dao actie find by username
        return customerService.customerByEmail(username.toString()).get(0);
    }

    @GetMapping(value= "/list")
    public List<Customer> customerList(){
        return customerService.list();

    }


    @GetMapping(value = "/KlantLijst")
    public List <CustomerDTO> list(){
        List<Customer> findAll = customerService.list();
        return customerConvertor.entityToDTO(findAll);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody Optional<Customer> findCustomerById(@PathVariable("id")int id) {
        return customerService.getCustomerById(id);

        @ResponseBody CustomerDTO findCustomerById ( @PathVariable("id") int id){
            return new CustomerDTO(customerService.findCustomerById(id).orElse(null));

        }
    }

    @GetMapping("/findByLastname")
    @ResponseBody Optional<CustomerDTO> findCustomerByName(@RequestParam("customerName")String name){
        return Optional.of(new CustomerDTO(customerService.findCustomerByName(name).orElse(null)));

    }
    @DeleteMapping(value = "/delete")
    @ResponseBody public void deleteCustomer(@PathVariable("id")int id){
        customerService.delete(id);
    }



    @GetMapping("/findByUsernamePassword")

    @ResponseBody Optional<Customer> findCustomerByUsernamePassword(@RequestParam("username") String username) {
        return customerService.findCustomerByUsernamePassword(username);

    @ResponseBody Optional<CustomerDTO> findCustomerByUsernamePassword(@RequestParam("username") String username) {
//        Optional<Customer> expectedCustomer =
//                customerService.findCustomerByUsernamePassword(username);
//        Customer dbCustomer = expectedCustomer.get();
//        customerConvertor.entityToDTO(dbCustomer);
//
//
//        return Optional.of(dbCustomer);
        return Optional.of(new CustomerDTO(customerService.findCustomerByUsernamePassword(username).orElse(null)));


    }


    @PostMapping(value = "/create")
    @ResponseBody
    ResponseEntity<?> createCustomer(@RequestBody Customer customer) throws NoSuchAlgorithmException {
        if (customer instanceof Customer) {
            customer.setPassword(hashpw(customer.getPassword(), gensalt(12) + customerAccount.getPEPPER()));
            customerService.storeCustomer(customer);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("User was successfully created");
    }



    @PostMapping(value = "/login")
    ResponseEntity<?> login(@RequestBody Account account) throws NoSuchAlgorithmException {
        Optional<Customer> expectedCustomer =
                customerService.findCustomerByUsernamePassword(account.getGebruikersnaam());
        Customer dbCustomer = expectedCustomer.get();
        String customer = account.getWachtwoord();
        if(BCrypt.checkpw(customer, dbCustomer.getPassword())){
            return ResponseEntity.ok(new Token(UUID.randomUUID()));
        }
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong username password combination");
    }

    @PostMapping("/save")
    public CustomerDTO save(@RequestBody CustomerDTO customerDTO){
        return null;
    }





}

record Token(UUID token){

}
