package com.example.cryptus.model;

import java.util.Objects;

public class Asset {
    int assetId;
    String assetNaam;
    String assetAfkorting;
    double koersEuro; //voorlopig alleen koers waarde crypto in euro; later eventueel koers is dollar toevoegen

    public Asset(int assetId, String assetNaam, String assetAfkorting, double koersEuro) {
        this.assetId = assetId;
        this.assetNaam = assetNaam;
        this.assetAfkorting = assetAfkorting;
        this.koersEuro = koersEuro;
    }

    public Asset(String assetNaam, String assetAfkorting, double koersEuro) {
        this(0, assetNaam, assetAfkorting, koersEuro);
    }

    public Asset() {
        this(0, null, null, 0);
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asset asset = (Asset) o;
        return assetId == asset.assetId && Double.compare(asset.koersEuro, koersEuro) == 0 && Objects.equals(assetNaam, asset.assetNaam) && Objects.equals(assetAfkorting, asset.assetAfkorting);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assetId, assetNaam, assetAfkorting, koersEuro);
    }

    @Override
    public String toString() {
        return "Asset{" +
                "assetId=" + assetId +
                ", assetNaam='" + assetNaam + '\'' +
                ", assetAfkorting='" + assetAfkorting + '\'' +
                ", koersEuro=" + koersEuro +
                '}';
    }
}
