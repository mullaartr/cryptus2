package com.example.cryptus.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class User {


    private int userId;
    private String firstName;
    private String preposition;
    private String lastName;
    private String userName;
    private String password;
    List<Transaction> transactionList;
    BankAccount bankAccount;

    Portefeuille portefeuille;

    @Autowired
    public User(int userId, String firstName, String preposition, String lastName, String userName, String password) {

        this.userId = userId;
        this.firstName = firstName;
        this.preposition = preposition;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
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
    /* public User(int userId, String firstName, String preposition, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.preposition = preposition;
        this.lastName = lastName ;
    }*/

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", preposition='" + preposition + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'';
    }
}
