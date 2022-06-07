package com.example.cryptus.model;

public class BankConfig {

    private double percentage;

    public BankConfig(double percentage) {
        this.percentage = percentage;
    }

    public BankConfig() {
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
