package com.example.cryptus.model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class Transaction{
    private final Logger logger = LoggerFactory.getLogger(Transaction.class);
    private int transactionId;
    private User koper;
    private User verkoper;
    private Asset asset;
    private double assetammount;
    private double euroammount;
    private int commisionPercentage;
    private LocalDateTime timestamp;

    public Transaction() {
        super();
        logger.info("Nieuwe transactie die de no arg constructor gebruikt.");
    }
    public Transaction(int transactionId, User koper, User verkoper,
                       Asset asset, double assetammount, double euroammount, int commisionPercentage, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.koper = koper;
        this.verkoper = verkoper;
        this.asset = asset;
        this.assetammount = assetammount;
        this.euroammount = euroammount;
        this.commisionPercentage = commisionPercentage;
        this.timestamp = timestamp;
        logger.info("Nieuwe transactie die de all arg constructor gebruikt.");
    }
    public Transaction(User koper, User verkoper, Asset asset,
                       double assetammount, double euroammount, int commisionPercentage) {
        this (0,koper, verkoper, asset,assetammount,euroammount,commisionPercentage, LocalDateTime.now());
        logger.info("Nieuwe transactie die de arg constructor voor de DB " +
                "gebruikt.");
    }

    public Logger getLogger() {
        return logger;
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

    public void setKoper(User koper) {
        this.koper = koper;
    }

    public User getVerkoper() {
        return verkoper;
    }

    public void setVerkoper(User verkoper) {
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
        //cryptohoeveelheid*koersineuro*percentage

        return 0;
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
