package com.example.cryptus.controller;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Transaction;
import com.example.cryptus.service.CustomerService;
import com.example.cryptus.service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "transactions")
public class TransactionController {
    private final Logger logger = LogManager.getLogger(CustomerController.class);
    private final TransactionService transactionService;
    private final CustomerService customerService;
    @Autowired
    public TransactionController(TransactionService transactionService,
                                 CustomerService customerService) {
        this.transactionService = transactionService;
        this.customerService = customerService;
        logger.info("Nieuwe TransactieController");
    }
    @GetMapping("/buytransactions_list")
    public List<Transaction> getBuyTransactionsFromUser(@RequestParam int userId) {
        List<Transaction> lijst = transactionService.getBuyTransactionsFromUser(userId);
        return lijst;
    }
    @GetMapping("/selltransactions_list")
    public List<Transaction> getSellTransactionsFromUser(@RequestParam int userId) {
        List<Transaction> lijst =
                transactionService.getSellTransactionsFromUser(userId);
        return lijst;
    }
    @PostMapping("/buytransaction_bank")
    public ResponseEntity<Optional<Transaction>> buyFromBank(@RequestParam String assetNaam, @RequestParam int assetAmount) {
        var username =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Customer buyer = customerService.findCustomerByEmail(username).get();
        transactionService.buyFromBank(buyer, assetNaam, assetAmount);
        return null;
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
