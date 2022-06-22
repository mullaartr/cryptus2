package com.example.cryptus.controller;

import com.example.cryptus.service.ApplicationUserService;
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
    private BankConfigService bankConfigService;
    private ApplicationUserService applicationUserService;
    @Autowired
    public AdminController(BankConfigService bankConfigService,
                           ApplicationUserService applicationUserService){
        this.bankConfigService = bankConfigService;
        this.applicationUserService = applicationUserService;
        logger.info("Nieuwe TransactieController");
    }
    @GetMapping("/get_percentage")
    public double getCommisionPercentage(){
        return bankConfigService.getPercentage();
    }
    @PostMapping("/set_percentage")
    public ResponseEntity<?> setCommisionPercentage(@RequestParam double percentage)
            throws NoSuchAlgorithmException {
        bankConfigService.updatePercentage(percentage);
        return ResponseEntity.ok().build();
    }
       @GetMapping("/get_status_user")
    public boolean getStatusUser(@RequestParam String username){
      return applicationUserService.loadUserByUsername(username).isAccountNonLocked();
    }

}
