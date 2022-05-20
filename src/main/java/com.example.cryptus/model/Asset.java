package com.example.cryptus.model;

public class Asset {
    int assetId;
    String assetNaam;
    String assetAfkorting;
    double koersEuro; //voorlopig alleen koers waarde crypto in euro; later eventueel koers is dollar toevoegen
    Portefeuille portefeuille;

    private double saldo;

    public Asset(int assetId, String assetNaam, String assetAfkorting, double koersEuro, Portefeuille portefeuille, Double saldo) {
        this.assetId = assetId;
        this.assetNaam = assetNaam;
        this.assetAfkorting = assetAfkorting;
        this.koersEuro = koersEuro;
        this.portefeuille = portefeuille;
        this.saldo = saldo;
    }

    public Asset(String assetNaam, String assetAfkorting, double koersEuro, Portefeuille portefeuille, double saldo) {
        this(0, assetNaam, assetAfkorting, koersEuro, portefeuille, saldo);
    }

    public Asset() {
        this(null, null, 0, null, 0);
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

    public Portefeuille getPortefeuille() {
        return portefeuille;
    }

    public void setPortefeuille(Portefeuille portefeuille) {
        this.portefeuille = portefeuille;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "assetId=" + assetId +
                ", assetNaam='" + assetNaam + '\'' +
                ", assetAfkorting='" + assetAfkorting + '\'' +
                ", koersEuro=" + koersEuro +
                ", portefeuille=" + portefeuille +
                '}';
    }
}
