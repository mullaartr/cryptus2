package com.example.cryptus.controller;

import com.example.cryptus.model.Configuration;
import com.example.cryptus.model.Transaction;
import com.example.cryptus.service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "transactions")

public class TransactionController {

    private final Logger logger = LogManager.getLogger(CustomerController.class);

    private final TransactionService transactionService;
    private Configuration configuration;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
        logger.info("Nieuwe TransactieController");
    }

    @GetMapping("/buytransactions_list")
    public List<Transaction> getBuyTransactionsFromUser(@RequestParam int userId) {
        List<Transaction> lijst = transactionService.getBuyTransactionsFromUser(userId);
        return lijst;
    }

    @PostMapping("/buytransaction_bank")
    public ResponseEntity<Optional<Transaction>> buyFromBank

            (@RequestParam int userIdKoper,
             @RequestParam int assetId,
             @RequestParam int assetAmount,
             @RequestParam int portefeuilleIdSeller)
    {

        Optional<Transaction> transaction = transactionService.buyFromBank(userIdKoper,
                assetId,
                assetAmount, portefeuilleIdSeller);
        return ResponseEntity.ok().body(transaction);
    }

    @PostMapping("/set_commission")
    public ResponseEntity<Configuration> setCommisionPercentage(@RequestParam int percentage) throws NoSuchAlgorithmException {
        Configuration.percentage = percentage;
        return ResponseEntity.ok().body(configuration);
    }

    @PostMapping(value = "/update_transaction/{transactionid}")
    public ResponseEntity<?> updateTransaction(@RequestBody Transaction transaction,
                                               @PathVariable("transactionid") int transactionId) {
        Optional<Transaction> opt =
                transactionService.updateTransaction(transaction, transactionId);
        if (opt.isPresent())
            return ResponseEntity.ok().body(transaction);
        else {
            return null;
        }
    }
    @DeleteMapping(value = "/delete_transaction/{transactionid}")
    @ResponseBody
    public ResponseEntity<?> deleteTransaction(@PathVariable("transactionid") int transactionId) {
        Optional<Transaction> opt =
                transactionService.findTransactionById(transactionId);
        if (opt.isPresent()) {
            transactionService.deleteTransaction(transactionId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
