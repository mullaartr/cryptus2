package com.example.cryptus.controller;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;
import com.example.cryptus.repository.CustomerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/customer/view")
public class CustomerViewController {

    private static final List<Customer> CUSTOMERS = Arrays.asList(
            new Customer(
                    1,
                    "Adam",
                    null,
                    "Hilversum",
                    "adamhilversum",
                    "adam@hilversum.nl",
                    Date.valueOf("1982-12-12"),
                    "12345678",
                    new Address(11, "Suchlaan", "1234BC", "Hilversum"),
                    "adam@hilversum.von",
                    "067373837463"),
            new Customer(
                    2,
                    "Baruch",
                    null,
                    "Spinoza",
                    "baruchspinoza",
                    "baruch@spinoza.nl",
                    Date.valueOf("1982-12-12"),
                    "12345678",
                    new Address(11, "Suchlaan", "1234BC", "Hilversum"),
                    "adam@hilversum.von",
                    "067373837463"),
            new Customer(
                    3,
                    "Alain",
                    null,
                    "Badiou",
                    "alainbadiou",
                    "alain@badiou.nl",
                    Date.valueOf("1982-12-12"),
                    "12345678",
                    new Address(11, "Suchlaan", "1234BC", "Hilversum"),
                    "adam@hilversum.von",
                    "067373837463"),
            new Customer(
                    4,
                    "James",
                    null,
                    "Bond",
                    "jamesbond",
                    "james@bond.nl",
                    Date.valueOf("1982-12-12"),
                    "12345678",
                    new Address(11, "Suchlaan", "1234BC", "Hilversum"),
                    "adam@hilversum.von",
                    "067373837463"),
            new Customer(
                    5,
                    "Tom",
                    "and",
                    "Jerry",
                    "tomjerry",
                    "tom@jerry.nl",
                    Date.valueOf("1982-12-12"),
                    "12345678",
                    new Address(11, "Suchlaan", "1234BC", "Hilversum"),
                    "adam@hilversum.von",
                    "067373837463")
    );

    @GetMapping(path = "{userId}")
    public Customer getCustomerView(@PathVariable("userId") Integer userId) {
        return CUSTOMERS.stream()
                .filter(customer -> userId.equals(customer.getUserId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Customer " + userId + " does not exist"));
    }

    @GetMapping(path = "/allcustomers")
    public List<Customer> getAllCustomerView(){
        return CUSTOMERS;
    }

}
