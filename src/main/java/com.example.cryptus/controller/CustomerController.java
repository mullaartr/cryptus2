package com.example.cryptus.controller;

import com.example.cryptus.dao.CustomerDao;
import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dao.Mapper;
import com.example.cryptus.dto.UserDTO;
import com.example.cryptus.model.Customer;
import com.example.cryptus.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/klant")
public class CustomerController {


    private CustomerService customerService;
    private final Logger logger = LogManager.getLogger(CustomerDaoJdbc.class);
    private Mapper mapper;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
        logger.info("New customerController");
    }


    @GetMapping
    public List<Customer> list() {
        return customerService.list();
    }

    @GetMapping(value = "/{id}")
    @ResponseBody Optional<Customer> findCustomerById(@PathVariable("id")int id){
        return  customerService.findCustomerById(id);
    }


    @GetMapping("/find")
    @ResponseBody Optional<Customer> findCustomerByName(@PathVariable("customerName")String name){
        return  customerService.findCustomerByName(name);
    }



    @PostMapping("/create")
    @ResponseBody List<Customer> createCustomer(@RequestBody Customer customer){
        customerService.storeCustomer(customer);
        return customerService.list();
    }

  /*  @GetMapping
    @ResponseBody
    public List<UserDTO> getUsers(){
        return customerService.list().stream().map(mapper::toDto).collect(Collectors.toList());
    }*/


}
