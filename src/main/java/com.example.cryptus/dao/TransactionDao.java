package com.example.cryptus.dao;

import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Transaction;
import com.example.cryptus.model.User;

import java.util.List;
import java.util.Optional;

public interface TransactionDao {

    void storeTransaction(Transaction transaction);
    void update(Transaction transaction);
    void deleteTransaction(int transactieId);
    List <Transaction> findTransactionsByUser(int userId);
    Optional<Transaction> findTransactionById(int transactionId);

}
