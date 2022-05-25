package com.example.cryptus.controller;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.model.Account;
import com.example.cryptus.model.Birthday;
import com.example.cryptus.model.Customer;
import com.example.cryptus.service.CustomerDTO;
import com.example.cryptus.service.CustomerService;
import com.example.cryptus.service.RegisterCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

import static org.mindrot.jbcrypt.BCrypt.gensalt;
import static org.mindrot.jbcrypt.BCrypt.hashpw;

@RestController
@RequestMapping(path = "registration")
public class RegisterCustomerController {


    private final CustomerDaoJdbc customerDaoJdbc;
    private CustomerService customerService;

    Account forUser = new Account("password");

    @Autowired
    public RegisterCustomerController( CustomerDaoJdbc customerDaoJdbc,
                                      CustomerService customerService) {

        this.customerDaoJdbc = customerDaoJdbc;
        this.customerService = customerService;
    }

    @PostMapping
    public String registration(@RequestBody Customer mpCustomer) throws NoSuchAlgorithmException {
//        ResponseEntity<?>
        if (mpCustomer instanceof Customer)
        {
            System.out.println(mpCustomer.getFirstName());
            mpCustomer.setPassword(hashpw(mpCustomer.getPassword(), gensalt(12) + forUser.getPEPPER()));
            customerService.storeCustomer(mpCustomer);
            System.out.println((mpCustomer.getPassword()).length());
            return "Congratulations " + mpCustomer.getFirstName() + ", you are now a registered member of Cryptus!";
        }
        else return "Your registration was incomplete, please try again. Thank you!";
    }
}
