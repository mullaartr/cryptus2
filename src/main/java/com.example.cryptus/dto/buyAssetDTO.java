package com.example.cryptus.dto;

public class buyAssetDTO {

    private double assetAmount;

    private String assetName;

    public buyAssetDTO(double assetAmount, String assetName) {
        this.assetAmount = assetAmount;
        this.assetName = assetName;
    }

    public double getAssetAmount() {
        return assetAmount;
    }

    public void setAssetAmount(double assetAmount) {
        this.assetAmount = assetAmount;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }
}
