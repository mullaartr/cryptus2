package com.example.cryptus.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Objects;

public class BankAccount {

private Customer accountHolder;
private String iban;
private double balance;
private int userId;

public static double MINIMUM_BALANCE = 0.0;


    @Autowired
    public BankAccount(String iban, double balance, int userId) {
        this.iban = iban;
        this.balance = balance;
        this.userId = userId;
    }

    public BankAccount(double balance, int MINIMUM_BALANCE) {
        this.balance = balance;
        this.MINIMUM_BALANCE = MINIMUM_BALANCE;
    }

    public BankAccount() {
    }

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public BankAccount(Customer accountHolder) {
        this(accountHolder.getBankAccount().getIban(),accountHolder.getBankAccount().getBalance(), accountHolder.getUserId());
    }

    public Customer getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(Customer accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return getUserId() == that.getUserId() && Objects.equals(getBalance(), that.getBalance());
    }



    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getBalance());
    }

    public double addFunds(double amount){
        if(amount > MINIMUM_BALANCE)
            balance += amount;
        else{
            throw new RuntimeException("Amount must be bigger than Zero");

        }

        return amount;
    }

    public double withdrawFunds(double amount){
        if(balance - amount >= MINIMUM_BALANCE)
            balance -= amount;
        else {
            throw new RuntimeException("Balance cant go below Zero");

        }

        return amount;
    }
    public boolean hasSufficientFunds(double amount){
        if( balance > amount){
            return  true;
        }else

        return false ;

    }






}

