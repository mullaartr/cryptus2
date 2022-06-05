package com.example.cryptus.controller;

import com.example.cryptus.dao.BankAccountDaoJdbc;
import com.example.cryptus.model.BankAccount;
import com.example.cryptus.model.Customer;
import com.example.cryptus.service.BankAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/bankaccount")
public class BankAccountController {

    private Customer customer;
    private BankAccountDaoJdbc bankAccountDaoJdbc;
    private BankAccountService bankAccountService;


    private final Logger logger = LogManager.getLogger(BankAccountDaoJdbc.class);

    @Autowired
    public BankAccountController(BankAccountDaoJdbc bankAccountDaoJdbc, BankAccountService bankAccountService) {
        this.bankAccountDaoJdbc = bankAccountDaoJdbc;
        this.bankAccountService = bankAccountService;

    }

    @GetMapping(value = "/bankaccountLijst")
    public List<BankAccount> list(){
        return bankAccountService.list();
    }

    @GetMapping(value = "/{id}")
        @ResponseBody Optional<BankAccount>findBankAccountByUserId(@PathVariable("id")int id){
        return bankAccountService.findBankaccountByUserId(id);
    }

    @PostMapping(value = "/create")
    @ResponseBody public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccount bankAccount){

        bankAccountService.store(bankAccount);
        return new ResponseEntity<BankAccount>(HttpStatus.CREATED);

    }





}
