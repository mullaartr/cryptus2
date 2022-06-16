package com.example.cryptus.model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction{
    private final Logger logger = LoggerFactory.getLogger(Transaction.class);
    private int transactionId;
    private Customer buyer;
    private Customer seller;
    private Asset asset;
    private double assetamount;
    private double euroamount;
    private double feePercentage;
    private LocalDateTime timestamp;

    public Transaction() {
        this(0, null, null, null, 0.00, 0.00, 0.00, LocalDateTime.now());
        logger.info("Nieuwe transactie die de no arg constructor gebruikt.");
    }
    public Transaction(int transactionId, Customer buyer, Customer seller,
                       Asset asset, double assetamount, double euroamount,
                       double feePercentage,
                       LocalDateTime creationTimestamp) {
        this.transactionId = transactionId;
        this.buyer = buyer;
        this.seller = seller;
        this.asset = asset;
        this.assetamount = assetamount;
        this.euroamount = euroamount;
        this.feePercentage = feePercentage;
        this.timestamp = creationTimestamp;
        logger.info("Nieuwe transactie die de all arg constructor gebruikt.");
    }
    public Transaction(Customer buyer, Customer seller, Asset asset,
                       double assetamount, double euroamount,double feePercentage) {

        this(0, buyer, seller, asset, assetamount, euroamount, feePercentage,
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

    public double getFeePercentage() {
        return feePercentage;
    }

    public void setFeePercentage(double feePercentage) {
        this.feePercentage = feePercentage;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double calcCommission(){
        return (euroamount)*(feePercentage /100.00);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionId == that.transactionId && Double.compare(that.assetamount, assetamount) == 0 && Double.compare(that.euroamount, euroamount) == 0 && Double.compare(that.feePercentage, feePercentage) == 0 && buyer.equals(that.buyer) && seller.equals(that.seller) && asset.equals(that.asset) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, buyer, seller, asset, assetamount, euroamount, feePercentage, timestamp);
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
                ", commisionPercentage=" + feePercentage +
                ", timestamp=" + timestamp +
                '}';
    }
}
