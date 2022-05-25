package com.example.cryptus.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class User {


    private int userId;
    private String firstName;
    private String preposition;
    private String lastName;
    private String userName;
    private String password;
    private String salt;
    List<Transaction> transactionList;
    BankAccount bankAccount;

    Portefeuille portefeuille;


    @Autowired
    public User(int userId, String firstName, String preposition, String lastName, String userName, String password, String salt) {
        this.userId = userId;
        this.firstName = firstName;
        this.preposition = preposition;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.salt = salt;
    }

    public User(String firstName, String preposition, String lastName, String userName, String password) {
        this.firstName = firstName;
        this.preposition = preposition;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }

    public User(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPreposition() {
        return preposition;
    }

    public void setPreposition(String preposition) {
        this.preposition = preposition;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Portefeuille getPortefeuille() {
        return portefeuille;
    }

    public void setPortefeuille(Portefeuille portefeuille) {
        this.portefeuille = portefeuille;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }


    @Override
    public String toString() {
        return
                "firstName= " + firstName + '\n' +
                " preposition= " + preposition + '\n' +
                " lastName= " + lastName + '\n' +
                " userName= " + userName + '\n' ;

    }
}
