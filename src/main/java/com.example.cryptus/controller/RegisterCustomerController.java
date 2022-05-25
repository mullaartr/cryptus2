package com.example.cryptus.controller;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dao.DaanMapper;
import com.example.cryptus.dao.transfer.RegisterDto;
import com.example.cryptus.model.Account;
import com.example.cryptus.model.Birthday;
import com.example.cryptus.model.Customer;
import com.example.cryptus.service.CustomerDTO;
import com.example.cryptus.service.CustomerService;
import com.example.cryptus.service.RegisterCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

import static org.mindrot.jbcrypt.BCrypt.gensalt;
import static org.mindrot.jbcrypt.BCrypt.hashpw;

@RestController
@RequestMapping(path = "users")
public class RegisterCustomerController {

    private final CustomerDaoJdbc customerDaoJdbc;
    //private RegistrationService registrationService;
   // private final CustomerDaoJdbc customerDaoJdbc; //not needed?
    private CustomerService customerService;
    private DaanMapper daanMapper; //change to Mapper is this works

    Account forUser = new Account("password");

    @Autowired
    public RegisterCustomerController( CustomerDaoJdbc customerDaoJdbc,
                                      CustomerService customerService) {

        this.customerDaoJdbc = customerDaoJdbc;
        this.customerService = customerService;
    }


//    public String registration(@RequestBody Customer mpCustomer) throws NoSuchAlgorithmException {
//        if (mpCustomer instanceof Customer)
//        {
//            System.out.println(mpCustomer.getFirstName());
//            mpCustomer.setPassword(hashpw(mpCustomer.getPassword(), gensalt(12) + forUser.getPEPPER()));
//            customerService.storeCustomer(mpCustomer);
//            System.out.println((mpCustomer.getPassword()).length());
//            return "Congratulations " + mpCustomer.getFirstName() + ", you are now a registered member of Cryptus!";
//        }
//        else return "Your registration was incomplete, please try again. Thank you!";

    //is hier iets overbodig met salt?
    @PostMapping
    public ResponseEntity<Customer> registration(@RequestBody RegisterDto registerDto) throws NoSuchAlgorithmException {
        Customer customer = new Customer(registerDto);
        customer.setPassword(hashpw(customer.getPassword(), gensalt(12) + forUser.getPEPPER()));
        customerService.storeCustomer(customer);
        return ResponseEntity.ok().body(customer);
    }
}
