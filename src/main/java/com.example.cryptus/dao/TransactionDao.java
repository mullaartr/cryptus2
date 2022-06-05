package com.example.cryptus.dao;

import com.example.cryptus.dto.TransactionDTO;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionDao {

    List<Transaction> findBuyTransactionsByUser(int userId);
    List<Transaction> findSellTransactionsByUser(int userId);
    void createTransaction(Transaction transaction);
    void update(Transaction transaction, int transactionId);
    void deleteTransaction(int transactionId);
    Optional<Transaction> findTransactionById(int transactionId);

}
