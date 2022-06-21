package com.example.cryptus.dto;
import com.example.cryptus.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class TransactionDTO {
    int transactieId;
    private AssetDTO asset;
    private double assetammount;
    private double euroammount;
    private double kosten;
    private LocalDateTime creationTimestamp;



    public TransactionDTO(Transaction transaction) {
        this.transactieId = transaction.getTransactionId();
        this.asset = new AssetDTO(transaction.getAsset());
        this.assetammount = transaction.getEuroamount();
        this.euroammount = transaction.getEuroamount();
    }

    public TransactionDTO(int transactieId, AssetDTO asset, double assetammount, double euroammount) {
        this.transactieId = transactieId;
        this.asset = asset;
        this.assetammount = assetammount;
        this.euroammount = euroammount;
    }

    public int getTransactieId() {
        return transactieId;
    }

    public void setTransactieId(int transactieId) {
        this.transactieId = transactieId;
    }

    public AssetDTO getAsset() {
        return asset;
    }

    public void setAsset(AssetDTO asset) {
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

    public double getKosten() {
        return kosten;
    }

    public void setKosten(double kosten) {
        this.kosten = kosten;
    }

    public boolean isEuroOfDollar() {
        return euroOfDollar;
    }

    public void setEuroOfDollar(boolean euroOfDollar) {
        this.euroOfDollar = euroOfDollar;
    }

    public void setKosten(int kosten) {
        this.kosten = kosten;
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
                ", assetammount=" + assetammount +
                ", euroammount=" + euroammount +
                ", commisionPercentage=" + kosten +
                ", timestamp=" + creationTimestamp +
                '}';
    }
}
