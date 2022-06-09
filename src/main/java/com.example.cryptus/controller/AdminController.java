package com.example.cryptus.controller;

import com.example.cryptus.service.BankConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(value = "admin")
public class AdminController {

    private final Logger logger = LogManager.getLogger(CustomerController.class);
    private final BankConfigService bankConfigService;

    @Autowired
    public AdminController(BankConfigService bankConfigService) {
        this.bankConfigService = bankConfigService;
        logger.info("Nieuwe TransactieController");
    }

    @GetMapping("/get_percentage")
    public double getCustomers(){
        return bankConfigService.getPercentage();
    }

    @PostMapping("/set_percentage")
    public ResponseEntity<?> setCommisionPercentage(@RequestParam int percentage) throws NoSuchAlgorithmException {
        bankConfigService.updatePercentage(percentage);
        return ResponseEntity.ok().build();
    }

}
