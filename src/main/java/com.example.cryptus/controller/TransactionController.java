package com.example.cryptus.controller;

import com.example.cryptus.model.Transaction;
import com.example.cryptus.service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping(value = "transaction")

public class TransactionController {

    private final Logger logger = LogManager.getLogger(CustomerController.class);

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
        logger.info("Nieuwe TransactieController");
    }

    @GetMapping("")
    @ResponseBody
    public String showHomePageTransactions() {
        return "Dit is de homepage van het transactiegedeelte";
    }

    @GetMapping("all_transactions")
    @ResponseBody
    public List<Transaction> getTransactionsFromUser(@RequestParam int userId) {
        return transactionService.getTransactions(userId);
    }

    @PostMapping("create_transaction")
    @ResponseBody
    public ResponseEntity<Transaction> createTransaction (@RequestBody Transaction transaction) throws NoSuchAlgorithmException {
        transactionService.createTransaction(transaction);
        return ResponseEntity.ok().body(transaction);
    }

    @PostMapping(value = "/update_transaction/{transactionid}/{assetammount}")
    @ResponseBody
    public ResponseEntity<Transaction> updateTransaction (@PathVariable ("transactionid") int transactionId,
                                                          @PathVariable ("assetammount") int assetAmount) {
        transactionService.updateTransaction(transactionId, assetAmount);
        return null;

    }

    @DeleteMapping(value = "/delete_transaction/{transactionid}")
    @ResponseBody
    public ResponseEntity<Transaction> deleteTransaction (@RequestBody Transaction transaction, @PathVariable("transactionid")int transactionId){
        transactionService.deleteTransaction(transaction, transactionId);
        return null;
    }


}
