/*
package com.example.cryptus.controller;

import com.example.cryptus.dao.transfer.DRegisterDto;
import com.example.cryptus.model.Customer;
import com.example.cryptus.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

public class DaanRegistrationController {

    @RestController
    @RequestMapping(value = "/newcustomer")
    public class RegisterController {

        private final Logger logger = LoggerFactory.getLogger(DaanRegistrationController.class);
        private CustomerService customerService;

        @Autowired
        public RegisterController(CustomerService customerService) {
            this.customerService = customerService;
            logger.info("New RegisterController.");
        }

        //Deze methode hebben we waarschijnlijk niet nodig voor de eerste sprint
        //Heeft denk ik te maken met weergave aan de front-end?
        @PostMapping("personaldetails")
        public String newCustomerHandler(
                @RequestParam(name="firstName") String firstName,
                @RequestParam(name="preposition") String preposition,
                @RequestParam(name="lastName") String lastName,
                @RequestParam(name="password") String password,
                @RequestParam(name="birthDate") String birthDate,
                @RequestParam(name="BSN") String BSN,
                @RequestParam(name="city") String city,
                @RequestParam(name="street") String street,
                @RequestParam(name="houseNumber") String houseNumber,
                @RequestParam(name="email") String email,
                @RequestParam(name="postcode") String postcode,
                @RequestParam(name="phone") String phone,
                Model model) {
            Customer newCustomer = new Customer(firstName, preposition, lastName, password, birthDate, BSN, city,
                    street, houseNumber, email, postcode, phone);
            model.addAttribute("customer", newCustomer);
            return "confirmation";
        }

        @PostMapping("register")
        public ResponseEntity<Customer> registerCustomerHandler (@RequestBody DRegisterDto DRegisterDto) {
            Customer customer = new Customer(DRegisterDto);
            customerService.storeCustomer(customer);//customerService.register(customer);
            return ResponseEntity.ok().body(customer);
        }

    }

}
*/
