package com.example.cryptus.dao;

import com.example.cryptus.model.BankAccount;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;

@Component
public interface BankAccountDao {

    Optional<BankAccount> findBankAccountByUserId(int id);
    void addFunds(double amount, int id);
    void withdrawFunds(double amount, int id);

    void store(BankAccount bankAccount);

    List<BankAccount> list();

    void update(BankAccount bankAccount);

    void delete(int userId);

    //update bankrekening set saldo = saldo + ? Where userId = 10
    //SELECT * FROM bankrekening where userId = 10
    // update bankrekening set saldo = saldo - ? Where userId = 10



}
