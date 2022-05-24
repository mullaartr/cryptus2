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
            "067373837463");

    public Customer loginSalutation() {
        return adam;
    }
}
