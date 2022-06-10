package com.example.cryptus.model;

import com.example.cryptus.dto.AssetDTO;
import com.example.cryptus.model.Koers;

import java.util.Objects;

public class Asset {
    private int assetId;
    private String assetNaam;
    private String assetAfkorting;
    private Koers koers; //van koers
    private double saldo; // van portefeuille_regel


    public Asset(int assetId, String assetNaam, String assetAfkorting, Koers koers, double saldo) {
        this.assetId = assetId;
        this.assetNaam = assetNaam;
        this.assetAfkorting = assetAfkorting;
        this.koers = koers;
        this.saldo = saldo;
    }

    public Asset(int assetId, String assetNaam, String assetAfkorting, Koers koers) {
        this.assetId = assetId;
        this.assetNaam = assetNaam;
        this.assetAfkorting = assetAfkorting;
        this.koers = koers;
    }

    public Asset(String assetNaam, String assetAfkorting, Koers koers, double saldo) {
        this(0, assetNaam, assetAfkorting, koers, saldo);
    }

    public Asset(AssetDTO assetDTO) {
        this(0, assetDTO.getAssetNaam(), assetDTO.getAssetAfkorting(), new Koers(assetDTO.getKoersDTO()), assetDTO.getSaldo());
    }

    public Asset(String assetNaam) {
        this(0, assetNaam, null, null, 0.0);
    }

    public Asset() {
        this(0, null, null, null, 0.0);
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
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

    public Koers getKoers() {
        return koers;
    }

    public void setKoers(Koers koers) {
        this.koers = koers;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asset asset = (Asset) o;
        return assetId == asset.assetId && Double.compare(asset.saldo, saldo) == 0 && assetNaam.equals(asset.assetNaam) && assetAfkorting.equals(asset.assetAfkorting) && koers.equals(asset.koers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assetId, assetNaam, assetAfkorting, koers, saldo);
    }

    @Override
    public String toString() {
        return "Asset{" +
                "assetId=" + assetId +
                ", assetNaam='" + assetNaam + '\'' +
                ", assetAfkorting='" + assetAfkorting + '\'' +
                ", koers=" + koers +
                ", saldo=" + saldo +
                '}';
    }
}
