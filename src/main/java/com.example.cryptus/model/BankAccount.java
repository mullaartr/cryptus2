package com.example.cryptus.model;

public class BankAccount {

    private User user;
    private String iban;
    private double saldo;

    public BankAccount(User user, String iban, double saldo) {
        this.user = user;
        this.iban = iban;
        this.saldo = saldo;
    }

    public User getUser() {
        return user;
    }

    public String getIban() {
        return iban;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
