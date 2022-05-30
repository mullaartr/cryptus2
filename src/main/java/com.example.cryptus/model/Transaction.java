package com.example.cryptus.model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class Transaction{
    private final Logger logger = LoggerFactory.getLogger(Transaction.class);
    private int transactionId;
    private Customer koper;
    private Customer verkoper;
    private Asset asset;
    private double assetammount;
    private double euroammount;
    private int commisionPercentage;
    private LocalDateTime timestamp;

    public Transaction() {
        super();
        this.timestamp = LocalDateTime.now();
        logger.info("Nieuwe transactie die de no arg constructor gebruikt.");
    }
    public Transaction(int transactionId, Customer koper, Customer verkoper,
                       Asset asset, double assetammount, double euroammount,
                       int commisionPercentage,
                       LocalDateTime creationTimestamp) {
        this.transactionId = transactionId;
        this.koper = koper;
        this.verkoper = verkoper;
        this.asset = asset;
        this.assetammount = assetammount;
        this.euroammount = euroammount;
        this.commisionPercentage = commisionPercentage;
        this.timestamp = creationTimestamp;
        logger.info("Nieuwe transactie die de all arg constructor gebruikt.");
    }
    public Transaction(Customer koper, Customer verkoper, Asset asset,
                       double assetammount, double euroammount, int commisionPercentage) {
        this (0,koper, verkoper, asset,assetammount,euroammount,
                commisionPercentage, LocalDateTime.now());
        logger.info("Nieuwe transactie die de arg constructor voor de DB " +
                "gebruikt.");
    }
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public User getKoper() {
        return koper;
    }

    public void setKoper(Customer koper) {
        this.koper = koper;
    }

    public User getVerkoper() {
        return verkoper;
    }

    public void setVerkoper(Customer verkoper) {
        this.verkoper = verkoper;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public double getAssetammount() {
        return assetammount;
    }

    public void setAssetammount(double assetammount) {
        this.assetammount = assetammount;
    }

    public double getEuroammount() {
        return euroammount;
    }

    public void setEuroammount(double euroammount) {
        this.euroammount = euroammount;
    }

    public int getCommisionPercentage() {
        return commisionPercentage;
    }

    public void setCommisionPercentage(int commisionPercentage) {
        this.commisionPercentage = commisionPercentage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double calcCommission(){

        double percentage = commisionPercentage;
        return (euroammount)*(percentage/100.00);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", koper=" + koper +
                ", verkoper=" + verkoper +
                ", asset=" + asset +
                ", assetammount=" + assetammount +
                ", euroammount=" + euroammount +
                ", commisionPercentage=" + commisionPercentage +
                ", timestamp=" + timestamp +
                '}';
    }
}
