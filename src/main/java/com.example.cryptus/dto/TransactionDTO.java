package com.example.cryptus.dto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class TransactionDTO {
    private final Logger logger = LoggerFactory.getLogger(TransactionDTO.class);
    private int transactionId;
    private String koperIban;
    private String verkoperIban;
    private int asset;
    private double assetammount;
    private double euroammount;
    private int commisionPercentage;
    private LocalDateTime creationTimestamp;

    public TransactionDTO() {
        super();
        logger.info("Nieuwe transactieDTO die de no arg constructor gebruikt.");
    }
    public TransactionDTO(int transactionId, String koper, String verkoper,
                          int asset, double assetammount, double euroammount,
                          int commisionPercentage, LocalDateTime creationTimestamp) {
        this.transactionId = transactionId;
        this.koperIban = koper;
        this.verkoperIban = verkoper;
        this.asset = asset;
        this.assetammount = assetammount;
        this.euroammount = euroammount;
        this.commisionPercentage = commisionPercentage;
        this.creationTimestamp = creationTimestamp;
        logger.info("Nieuwe transactieDTO die de all arg constructor gebruikt" +
                ".");
    }
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getKoperIban() {
        return koperIban;
    }

    public void setKoperIban(String koperIban) {
        this.koperIban = koperIban;
    }

    public String getVerkoperIban() {
        return verkoperIban;
    }

    public void setVerkoperIban(String verkoperIban) {
        this.verkoperIban = verkoperIban;
    }

    public int getAsset() {
        return asset;
    }

    public void setAsset(int asset) {
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

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "transactionId=" + transactionId +
                ", koperIban='" + koperIban + '\'' +
                ", verkoperIban='" + verkoperIban + '\'' +
                ", asset=" + asset +
                ", assetammount=" + assetammount +
                ", euroammount=" + euroammount +
                ", commisionPercentage=" + commisionPercentage +
                ", timestamp=" + creationTimestamp +
                '}';
    }
}
