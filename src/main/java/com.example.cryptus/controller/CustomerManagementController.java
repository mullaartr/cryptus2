package com.example.cryptus.controller;

import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("manage/customers")
public class CustomerManagementController {

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

//    hasRole("ROLE_") hasAnyRole("ROLE_") hasAuthority("permission") hasAnyAuthority("permission")

    @GetMapping(path = "/allcustomers")
    public List<Customer> getAllCustomers(){
        System.out.println("getAllCustomers");
        return CUSTOMERS;
    }

    @PostMapping(path = "{customerId}")
    public void registerNewCustomer(@RequestBody Customer customer) {
        System.out.println("registerNewCustomer");
        System.out.println(customer);
    }

    @DeleteMapping(path = "{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer customerId){
        System.out.println("deleteCustomer");
        System.out.println(customerId);
    }

    @PutMapping(path = "{customerId}")
    public void updateCustomer(@PathVariable("{customerId}") Integer customerId, @RequestBody Customer customer){
        System.out.println("updateCustomer");
        System.out.println(String.format("%s %s", customerId, customer));
    }
}
