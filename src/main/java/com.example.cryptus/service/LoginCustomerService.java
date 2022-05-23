package com.example.cryptus.service;

import com.example.cryptus.model.Account;
import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Date;

@Service
public class LoginCustomerService {

    Account forUser = new Account("password");

    Customer adam = new Customer(0,
            "Adam",
            "von",
            "Hilversum",
            "password", // forUser.hashSaltNPepper("password"),
            "alsomail@email.com",
            Date.valueOf("1982-12-12"),
            "12345678",
            new Address(42, "In the middle of Nowhere", "0908IO", "Hilversum" ),
            "adam@hilversum.von",
            "908070606", //"Babylon",
             "**********");

    public Customer loginSalutation() {
        return adam;
    }
}

