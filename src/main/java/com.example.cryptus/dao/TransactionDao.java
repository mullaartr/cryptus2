package com.example.cryptus.dao;

import com.example.cryptus.dto.TransactionDTO;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionDao {

    void createTransaction(Transaction transaction);
    void update(int transactionId, int assetAmount);
     void deleteTransaction(int transactionId);
    List<TransactionDTO> findTransactionsByUser(Customer customer);
    Optional<Transaction> findTransactionById(int transactionId);

}
