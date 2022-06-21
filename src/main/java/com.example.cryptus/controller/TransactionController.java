package com.example.cryptus.controller;
import com.example.cryptus.dto.TransactionDTO;
import com.example.cryptus.dto.buyAssetDTO;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Transaction;
import com.example.cryptus.service.CustomerService;
import com.example.cryptus.service.Exceptions.NotEnoughAssetsAcception;
import com.example.cryptus.service.Exceptions.NotEnoughSaldoException;
import com.example.cryptus.service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "transactions")
public class TransactionController <T> {
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


    @GetMapping(value = "marktplaatsAankoop")
    public List<TransactionDTO> vulMarktplaatsAanbod(){
        List<Transaction> openstaandeTransacties = transactionService.toonAanbod();
        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        openstaandeTransacties.forEach(transaction -> transactionDTOS.add(new TransactionDTO(transaction)));
        return transactionDTOS;
    }

    @GetMapping(value = "marktplaatsOpkoop")
    public List<TransactionDTO> vulMarktplaatsOpbod(){
        List<Transaction> openstaandeTransacties = transactionService.toonOpbod();
        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        openstaandeTransacties.forEach(transaction -> transactionDTOS.add(new TransactionDTO(transaction)));
        return transactionDTOS;
    }

    // respons
    @PostMapping("/buytransaction_bank")
    public ResponseEntity<?> buyFromBank(@RequestBody buyAssetDTO buyAssetDTO) throws NotEnoughSaldoException {
        var username =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        int userIdBuyer =
                customerService.customerByEmail(username).get(0).getUserId();
        Customer buyer =
                customerService.findCustomerById(userIdBuyer).get();
        try {
            Transaction transaction = transactionService.buyFromBank(buyer, buyAssetDTO.getAssetName(), buyAssetDTO.getAssetAmount());
            if (transaction != null) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Bedankt voor uw aankoop. U crypto's zijn toegevoegd aan uw portefeuille");
            }
        }catch (NotEnoughSaldoException notEnoughSaldoException){
            return new ResponseEntity<>("Je hebt op dit moment niet genoeg " +
                    "saldo op je bankrekening", HttpStatus.NOT_ACCEPTABLE);
        } catch (NotEnoughAssetsAcception notEnoughAssetsAcception){
            return new ResponseEntity<>("De bank heeft op dit moment niet genoeg van deze currency in zijn bezit", HttpStatus.NOT_ACCEPTABLE);
        }
        return null;
    }


    @PostMapping(value = "/update_transaction/{transactionid}")
    public ResponseEntity<?> updateTransaction(@RequestBody Transaction transaction,
                                               @PathVariable("transactionid") int transactionId) {
        transaction.setTransactionId(transactionId);
        Optional<Transaction> opt =
                transactionService.updateTransaction(transaction);
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
