package com.example.cryptus.controller;

import com.example.cryptus.dao.BankAccountDaoJdbc;
import com.example.cryptus.model.BankAccount;
import com.example.cryptus.model.Customer;
import com.example.cryptus.repository.BankAccountRepository;
import com.example.cryptus.service.BankAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import static com.example.cryptus.model.BankAccount.MINIMUM_BALANCE;

@RestController
@RequestMapping(value = "/bankaccount")
public class BankAccountController {

    private Customer customer;
    private BankAccount bankAccount;
    private BankAccountDaoJdbc bankAccountDaoJdbc;
    private BankAccountService bankAccountService;
    private BankAccountRepository bankAccountRepository;


    private final Logger logger = LogManager.getLogger(BankAccountDaoJdbc.class);

    @Autowired
    public BankAccountController( BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;

    }
    @PatchMapping(value = "/deposit")
    @ResponseBody
    public ResponseEntity<?> addFunds(@RequestParam double amount, @RequestParam int id){
        if(amount <= 0){
            System.out.println("hellooooooo");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Amount must be bigger than 0");

        }
        else{
            bankAccountService.addFunds(amount,id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Funds were added Successfully");

        }


    }

    @PatchMapping(value = "/withdraw")
    @ResponseBody
    public ResponseEntity<?> withdrawFunds(@RequestParam double amount, @RequestParam int id){

        Optional<BankAccount> lookUpAccount =
                bankAccountService.findBankaccountByUserId(id);
        BankAccount foundAccount = lookUpAccount.get();
        if(foundAccount.hasSufficientFunds(amount))
            bankAccountService.withdrawFunds(amount, id);
        else{
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No sufficient funds");
        }

        return new ResponseEntity<BankAccount>(HttpStatus.ACCEPTED);

    }



    @PatchMapping(value = "/update")
    @ResponseBody
    public ResponseEntity<BankAccount> update(@RequestBody BankAccount bankAccount){
        bankAccountService.update(bankAccount);
        return new ResponseEntity<BankAccount>(HttpStatus.ACCEPTED);

    }

    @GetMapping(value = "/bankaccountLijst")
    public List<BankAccount> list(){
        return bankAccountService.list();
    }

    @GetMapping(value = "/{id}")
        @ResponseBody Optional<BankAccount>findBankAccountByUserId(@PathVariable("id")int id){
        return bankAccountService.findBankaccountByUserId(id);
    }

    @PostMapping(value = "/create" , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccount bankAccount){

        bankAccountService.store(bankAccount);
        return new ResponseEntity<BankAccount>(HttpStatus.CREATED);

    }





}
