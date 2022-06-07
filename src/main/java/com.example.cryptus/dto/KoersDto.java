package com.example.cryptus.dto;

public class KoersDto {

    double koersInEuro;
    double koersInDollars;
    String assetNaam;

    public KoersDto(double koersInEuro, double koersInDollars, String assetNaam) {
        this.koersInEuro = koersInEuro;
        this.koersInDollars = koersInDollars;
        this.assetNaam = assetNaam;
    }

    public double getKoersInEuro() {
        return koersInEuro;
    }

    public void setKoersInEuro(double koersInEuro) {
        this.koersInEuro = koersInEuro;
    }

    public double getKoersInDollars() {
        return koersInDollars;
    }

    public void setKoersInDollars(double koersInDollars) {
        this.koersInDollars = koersInDollars;
    }

    public String getAssetNaam() {
        return assetNaam;
    }

    public void setAssetNaam(String assetNaam) {
        this.assetNaam = assetNaam;
    }
}
