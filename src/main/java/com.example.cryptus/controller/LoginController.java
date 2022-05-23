package com.example.cryptus.controller;

import com.example.cryptus.model.Account;
import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.User;
import com.example.cryptus.service.AuthenticatieService;
import com.example.cryptus.service.CustomerDTO;
import com.example.cryptus.service.CustomerService;
import com.example.cryptus.service.LoginCustomerService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "login")
public class LoginController {
    private final LoginCustomerService loginCustomerService;
    private AuthenticatieService authenticatieService;
    private CustomerService customerService;

    Account forUser = new Account("password");

    String wordForPassword = forUser.hashSaltNPepper("password");

    Customer adam = new Customer(
            1,
            "Adam",
            "von",
            "Hilversum",
            forUser.hashSaltNPepper("password"),
            "alsomail@email.com",
            Date.valueOf("1982-12-12"),
            "12345678",
           new Address(11, "Suchlaan", "1234BC", "Hilversum"),
            "adam@hilversum.von",
            "067373837463",
            "**********"
    );

    Customer devil = new Customer(
            1,
            "Adam",
            "von",
            "Hilversum",
            forUser.hashSaltNPepper("password"),
            "alsomail@email.com",
            Date.valueOf("1982-12-12"),
            "12345678",
            new Address(11, "Suchlaan", "1234BC", "Hilversum"),
            "adam@hilversum.von",
            "067373837463",
            "**********"
    );

    @Autowired
    LoginController(LoginCustomerService loginCustomerService) {
        this.loginCustomerService = loginCustomerService;
    }

    @PostMapping
    public String login(@RequestBody Account mpAccount){
        if (mpAccount.getGebruikersnaam().equals(adam.getUserName())
                && BCrypt.checkpw(mpAccount.getWachtwoord(), adam.getPassword())){
            return "Hello " + adam.getFirstName() + ", you are now logged in to Cryptus!";
        }
        else return "The email and/or password you entered did not match our records. Please double check and try " +
                "again.";
    }

    //Daan: I'm working on this method
    //Need a constructor in CustomerDTO, based
//    @PostMapping
//    public ResponseEntity<CustomerDTO> loginHandler(@RequestBody Account account) {
//        boolean checkCustomer = authenticatieService.authenticate(account.getGebruikersnaam(), account.getWachtwoord());
//        Optional<Customer> customer = null;
//        if (checkCustomer) {
//            customer = customerService.findCustomerByUsernamePassword(
//                    account.getGebruikersnaam());//.orElse(null);
//        }
//        return ResponseEntity.ok().body(new CustomerDTO(customer));
//    }

}
