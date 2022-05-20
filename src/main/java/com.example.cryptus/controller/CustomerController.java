package com.example.cryptus.controller;

import com.example.cryptus.dao.CustomerDao;
import com.example.cryptus.model.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {


    private CustomerDao customerDao;

    public CustomerController(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


/*    @GetMapping("/klant")
    public List<Customer> getCustomers(){
        return customerDao.list();
    }

    @GetMapping("/klant")
    Optional<Customer> findCustomerById(int id){
        return  customerDao.findCustomerById(id);
    }

    @GetMapping("/klant")
    Optional<Customer> findCustomerByName(String name){
        return  customerDao.findCustomerByName(name);
    }*/


}
