package com.example.cryptus.service;

import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Transaction;
import com.example.cryptus.repository.CustomerRepository;
import com.example.cryptus.repository.TransactionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class TransactionService {

    private final Logger logger = LogManager.getLogger(CustomerService.class);
    private CustomerRepository customerRepository;
    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              CustomerRepository customerRepository) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
        logger.info("Nieuwe TransactieService");
    }

    public List<Transaction> getTransactions(int userId) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomerById(userId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            List<Transaction> transactions =
                    transactionRepository.findTransactionsByUser(customer);
            return transactions;
        }
        return new ArrayList<>();
    }

    public void createTransaction(Transaction transaction) {
        transactionRepository.createTransaction(transaction);
    }

    public void updateTransaction(int transactionId, int assetAmount) {
        transactionRepository.updateTransaction(transactionId, assetAmount);
    }

    public void deleteTransaction(Transaction transaction, int id) {
        transactionRepository.deleteTransaction(transaction, id);
    }

}
