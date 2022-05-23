package com.example.cryptus.controller;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.model.Account;
import com.example.cryptus.model.Birthday;
import com.example.cryptus.model.Customer;
import com.example.cryptus.service.CustomerDTO;
import com.example.cryptus.service.CustomerService;
import com.example.cryptus.service.RegisterCustomerService;
import com.example.cryptus.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(path = "registration")
public class RegisterCustomerController {

    private RegistrationService registrationService;
    private final CustomerDaoJdbc customerDaoJdbc;
    private CustomerService customerService;

    Account forUser = new Account("password");

    @Autowired
    public RegisterCustomerController(RegistrationService registrationService, CustomerDaoJdbc customerDaoJdbc,
                                      CustomerService customerService) {
        this.registrationService = registrationService;
        this.customerDaoJdbc = customerDaoJdbc;
        this.customerService = customerService;
    }

    @PostMapping
    public String registration(@RequestBody Customer mpCustomer) throws NoSuchAlgorithmException {
        if (mpCustomer instanceof Customer)
        {
            registrationService.register(mpCustomer);
//            mpCustomer.setPassword(forUser.hashSaltNPepper(mpCustomer.getPassword()));
//            customerService.storeCustomer(mpCustomer);
//            System.out.println((mpCustomer.getPassword()).length());
//            customerDaoJdbc.storeCustomer(mpCustomer);
            return "Congratulations " + mpCustomer.getFirstName() + ", you are now a registered member of Cryptus!";
        }
//        {
//            RegistrationService registrationService = new RegistrationService((new CustomerDaoJdbc(new JdbcTemplate())));
//            registrationService.register(mpCustomer);
//            return "Congratulations " + mpCustomer.getFirstName() + ", you are now a registered member of Cryptus!";
//        }
        else return "Your registration was incomplete, please try again. Thank you!";
    }

}
