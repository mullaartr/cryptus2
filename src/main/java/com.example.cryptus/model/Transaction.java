package com.example.cryptus.model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class Transaction{
    private final Logger logger = LoggerFactory.getLogger(Transaction.class);
    private int transactionId;
    private Customer buyer;
    private Customer seller;
    private Asset asset;
    private double assetamount;
    private double euroamount;
    private int commisionPercentage;
    private LocalDateTime timestamp;

    public Transaction() {
        this(0, null, null, null, 0.00, 0.00, LocalDateTime.now());
        logger.info("Nieuwe transactie die de no arg constructor gebruikt.");
    }
    public Transaction(int transactionId, Customer buyer, Customer seller,
                       Asset asset, double assetamount, double euroamount,
                       LocalDateTime creationTimestamp) {
        this.transactionId = transactionId;
        this.buyer = buyer;
        this.seller = seller;
        this.asset = asset;
        this.assetamount = assetamount;
        this.euroamount = euroamount;
        this.commisionPercentage = Configuration.percentage; //instelbaar maken
        this.timestamp = creationTimestamp;
        logger.info("Nieuwe transactie die de all arg constructor gebruikt.");
    }
    public Transaction(Customer buyer, Customer seller, Asset asset,
                       double assetamount, double euroamount) {

        this(0, buyer, seller, asset, assetamount, euroamount,
                LocalDateTime.now());

        logger.info("Nieuwe transactie die de arg constructor voor de DB " +
                "gebruikt.");
    }
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(Customer buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(Customer seller) {
        this.seller = seller;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public double getAssetamount() {
        return assetamount;
    }

    public void setAssetamount(double assetamount) {
        this.assetamount = assetamount;
    }

    public double getEuroamount() {
        return euroamount;
    }

    public void setEuroamount(double euroamount) {
        this.euroamount = euroamount;
    }

    public int getCommisionPercentage() {
        return commisionPercentage;
    }

    public void setCommisionPercentage(int commisionPercentage) {
        this.commisionPercentage = commisionPercentage; // hoe kan ik dit instelbaar maken en
        // omvormen tot iets dat je van buitenaf kunt aanpassen
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double calcCommission(){
        return (euroamount)*(commisionPercentage/100.00);
    }



    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", koper=" + buyer +
                ", verkoper=" + seller +
                ", asset=" + asset +
                ", assetammount=" + assetamount +
                ", euroammount=" + euroamount +
                ", commisionPercentage=" + commisionPercentage +
                ", timestamp=" + timestamp +
                '}';
    }
}
