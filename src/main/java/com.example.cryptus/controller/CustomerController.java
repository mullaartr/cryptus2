package com.example.cryptus.controller;
import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.model.Customer;
import com.example.cryptus.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/klant")
public class CustomerController {


    private CustomerService customerService;
    private final Logger logger = LogManager.getLogger(CustomerDaoJdbc.class);

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
        logger.info("New customerController");
    }


    @GetMapping(value = "/KlantLijst")
    public List<Customer> list(){
        return customerService.list();
    }

    @GetMapping(value = "/{id}")
    @ResponseBody Optional<Customer> findCustomerById(@PathVariable("id")int id){
        return  customerService.findCustomerById(id);
    }

    @GetMapping("/findByLastname")
    @ResponseBody
    Optional<Customer> findCustomerByName(@RequestParam("customerName")String name){
        return  customerService.findCustomerByName(name);
    }



<<<<<<< HEAD


    @GetMapping("/findByUsernamePassword")
    @ResponseBody Optional<Customer> findCustomerByUsernamePassword(@RequestParam("username") String username){
        return  customerService.findCustomerByUsernamePassword(username);
=======
    @GetMapping(value="/findByUsernamePassword")
    @ResponseBody Optional<Customer> findCustomerByUsernamePassword(@RequestParam("username") String username, @RequestParam("password") String password) {
        return customerService.findCustomerByUsernamePassword(username);
>>>>>>> 3e0e67bb778b45da79c2f592816839ba81056a38
    }



    @PostMapping(value = "/create")
    @ResponseBody List<Customer> createCustomer(@RequestBody Customer customer){
        customerService.storeCustomer(customer);
        return customerService.list();
    }




}
