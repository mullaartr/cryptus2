package com.example.cryptus.service;

import com.example.cryptus.model.Account;
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

    public Customer loginSalutation() {
        return adam;
    }
}
