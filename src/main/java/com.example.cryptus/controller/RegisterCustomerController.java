package com.example.cryptus.controller;

import com.example.cryptus.model.Birthday;
import com.example.cryptus.model.Customer;
import com.example.cryptus.service.CustomerDTO;
import com.example.cryptus.service.RegisterCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "registration")
public class RegisterCustomerController {

    private final RegisterCustomerService registerCustomerService;

    @Autowired
    public RegisterCustomerController(RegisterCustomerService registerCustomerService) {
        this.registerCustomerService = registerCustomerService;
    }

//    @PostMapping
//    public String registration(@RequestBody CustomerDTO mpCustomerDTO){
//        if (mpCustomer instanceof Customer){
//            registerCustomerService.getListOfCustomers().add(mpCustomer);
//            return "Congratulations " + mpCustomer.getFirstName() + ", you are now a registered member of Cryptus!";
//        }
//        else return "Your registration was incomplete, please try again. Thank you!";
//    }

//        @PostMapping
//    public Birthday birthdayRegister(@RequestBody CustomerDTO mpCustomerDTO){
//        Birthday adamsbirthday = new Birthday((mpCustomerDTO.getBirthDate()));
//    }
}
