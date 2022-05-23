package com.example.cryptus.service;

import com.example.cryptus.model.Customer;

import java.util.List;

public class CustomerLoginService {

    /*Account account;
    Customer customer;

    Account forUser = new Account("password");

    Customer loginCustomer = new Customer(
            0,
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

    public Customer findCustByUserName() { //public Customer findCustByUserName(String userName) {
        //if ( userName == "alsomail@email.com" ) {
            return loginCustomer;
        //} else return null;
    }*/

    /*List<Customer> customers;
    public List<Customer> saveActiveCustomer(Customer activeCustomer) {
        customers.add(activeCustomer);
        System.out.println(customers);
        return customers;
    }*/

    public Customer saveOne(Customer customer) {
        System.out.println(customer);
        return customer;
    }

}
