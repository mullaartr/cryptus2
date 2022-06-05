package com.example.cryptus.model;


import com.example.cryptus.dao.transfer.RegisterDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class User implements Serializable {

    //Daan: I made a logger
    private final transient Logger logger = LoggerFactory.getLogger(User.class);

    private int userId;
    private String firstName;
    private String preposition;
    private String lastName;
    private String userName;
    private String password;
    private List<Transaction> transactionList;
    private BankAccount bankAccount;

    private Portefeuille portefeuille;

    @Autowired
    public User(int userId, String firstName, String preposition, String lastName, String userName, String password) {

        this.userId = userId;
        this.firstName = firstName;
        this.preposition = preposition;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }

    //Daan: I created this no-args constructor
    public User() {
        super();
        logger.info("User created with no-args constructor");
    }

    //Daan: I created this constructor for registration
    public User(String firstName, String preposition, String lastName, String userName, String password, BankAccount bankAccount) {
        this.firstName = firstName;
        this.preposition = preposition;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.bankAccount = bankAccount;
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


    @Override
    public String toString() {

        return
                "firstName= " + firstName + '\n' +
                        " preposition= " + preposition + '\n' +
                        " lastName= " + lastName + '\n' +
                        " userName= " + userName + '\n';


    }
}
