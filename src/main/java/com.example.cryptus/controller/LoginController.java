package com.example.cryptus.controller;

import com.example.cryptus.model.Account;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.User;
import com.example.cryptus.service.AuthenticatieService;
import com.example.cryptus.service.LoginCustomerService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping(path = "login")
public class LoginController {
    private final LoginCustomerService loginCustomerService;
    private AuthenticatieService authenticatieService;

    Account forUser = new Account("password");

    String wordForPassword = forUser.hashSaltNPepper("password");

    /*Customer adam = new Customer(1,
            "Adam",
            "von",
            "Hilversum",
            Date.valueOf("1982-12-12"),
            "12345678",
            "In the middle of Nowhere",
            42,
            "0908IO",
            "Babylon",
            "adam@hilversum.von",
            908070606,
            "alsomail@email.com",
            forUser.hashSaltNPepper("password"),
            "**********");

    Customer devil = new Customer(0,
            "Devil",
            "von",
            "Devilius",
            Date.valueOf("1982-12-12"),
            "12345678",
            "In the middle of Nowhere",
            42,
            "0908IO",
            "Hell",
            "adam@hilversum.von",
            908070606,
            "alsomail@email.com",
            forUser.hashSaltNPepper("idunno"),
            "**********");
*/
    @Autowired
    LoginController(LoginCustomerService loginCustomerService) {
        this.loginCustomerService = loginCustomerService;
    }

//    @GetMapping
//    public Customer loginSalutation() {
//        return loginCustomerService.loginSalutation();
//    }
//
//    @PostMapping
//    public Customer checkUsernameAndPassword(@RequestBody Account mpAccount){
//        if (mpAccount.getGebruikersnaam().equals(adam.getUserName())
//                && BCrypt.checkpw(mpAccount.getWachtwoord(), adam.getPassword())){
//            return adam;
//        }
//        else return devil;
//    }

}
