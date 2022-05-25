package com.example.cryptus.dao;

import com.example.cryptus.model.Transaction;
import com.example.cryptus.model.User;

import java.util.List;
import java.util.Optional;

public interface TransactionDao {

    void createTransaction(Transaction transaction);
    void update(Transaction transaction);
    void deleteTransaction(int transactionId);
    List <Transaction> findTransactionsByUser(int userId);
    Optional<Transaction> findTransactionById(int transactionId);

}
