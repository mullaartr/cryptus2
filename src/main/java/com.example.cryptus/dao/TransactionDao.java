package com.example.cryptus.dao;

import com.example.cryptus.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionDao {

    List<Transaction> findTransactions();
    List<Transaction> findBuyTransactionsByUser(int userId);
    List<Transaction> findSellTransactionsByUser(int userId);
    int createTransaction(Transaction transaction);
    void update(Transaction transaction);
    void deleteTransaction(int transactionId);
    Optional<Transaction> findTransactionById(int transactionId);

}
