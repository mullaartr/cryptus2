package com.example.cryptus.model;

//import com.example.cryptus.model.Koers;

import java.util.Objects;

public class Asset {
    private int assetId;
    private String assetNaam;
    private String assetAfkorting;
    private double koersEuro;//heb hier tijdelijk een double van gemaakt om de boel draaiende te houden

    private double saldo;


    public Asset(int assetId, String assetNaam, String assetAfkorting, double koersEuro, double saldo) {
        this.assetId = assetId;
        this.assetNaam = assetNaam;
        this.assetAfkorting = assetAfkorting;
        this.koersEuro = koersEuro;
        this.saldo = saldo;
    }

    public Asset(String assetNaam, String assetAfkorting, double koersEuro) {
        this(0, assetNaam, assetAfkorting, koersEuro, 0.0);
    }

    public Asset() {
        this(0, null, null, 0, 0.0);

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

    public double getKoersEuro() {
        return koersEuro;
    }

    public void setKoersEuro(double koersEuro) {
        this.koersEuro = koersEuro;
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
        return assetId == asset.assetId && Double.compare(asset.koersEuro, koersEuro) == 0 && Double.compare(asset.saldo, saldo) == 0 && Objects.equals(assetNaam, asset.assetNaam) && Objects.equals(assetAfkorting, asset.assetAfkorting);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assetId, assetNaam, assetAfkorting, koersEuro, saldo);
    };

    @Override
    public String toString() {
        return "Asset{" +
                "assetId=" + assetId +
                ", assetNaam='" + assetNaam + '\'' +
                ", assetAfkorting='" + assetAfkorting + '\'' +
                ", koersEuro=" + koersEuro +
                ", saldo=" + saldo +
                '}';
    }
}
