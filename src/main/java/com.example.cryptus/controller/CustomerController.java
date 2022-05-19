package com.example.cryptus.controller;

import com.example.cryptus.dao.CustomerDao;
import com.example.cryptus.model.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {


    private CustomerDao customerDao;

    public CustomerController(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


    @GetMapping("/klant")
    public List<Customer> getCustomers(){
        return customerDao.list();
    }


}
