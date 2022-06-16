package com.example.cryptus.dto;

import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Koers;

public class AssetDTO {

    private int assetId;
    private String assetNaam;
    private String assetAfkorting;
    private KoersDto koersDTO;
    private double saldo;

    public AssetDTO(Asset asset) {
        this.assetId = asset.getAssetId();
        this.assetNaam = asset.getAssetNaam();
        this.assetAfkorting = asset.getAssetAfkorting();
        this.saldo = asset.getSaldo();
    }

    public String getAssetNaam() {
        return assetNaam;
    }

    public void setAssetNaam(String assetNaam) {
        this.assetNaam = assetNaam;
    }

    public String getAssetAfkorting() {
        return assetAfkorting;
    }

    public void setAssetAfkorting(String assetAfkorting) {
        this.assetAfkorting = assetAfkorting;
    }

    public KoersDto getKoersDTO() {
        return koersDTO;
    }

    public void setKoersDTO(KoersDto koersDTO) {
        this.koersDTO = koersDTO;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
