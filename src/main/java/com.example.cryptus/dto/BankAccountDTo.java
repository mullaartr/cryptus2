package com.example.cryptus.dto;

import com.example.cryptus.model.BankAccount;

public class BankAccountDTo {

    private Double balance;

    private String iban;

    public BankAccountDTo() {
    }

    public BankAccountDTo(BankAccount bankAccount) {
        this.balance = bankAccount.getBalance();
        this.iban = bankAccount.getIban();
    }


    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
