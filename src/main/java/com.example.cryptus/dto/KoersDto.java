package com.example.cryptus.dto;

import java.time.LocalDateTime;

public class KoersDto {

    double koersInEuro;
    double koersInDollars;
    String assetNaam;
    LocalDateTime koersDatum;

    //todo LocalDateTime koersDatum hierin verwerken

    public KoersDto(double koersInEuro, double koersInDollars, LocalDateTime koersDatum, String assetNaam) {
        this.koersInEuro = koersInEuro;
        this.koersInDollars = koersInDollars;
        this.koersDatum = koersDatum;
        this.assetNaam = assetNaam;
    }

    public KoersDto(double koersInEuro, double koersInDollars) {
        this.koersInEuro = koersInEuro;
        this.koersInDollars = koersInDollars;
    }

    public LocalDateTime getKoersDatum() {
        return koersDatum;
    }

    public void setKoersDatum(LocalDateTime koersDatum) {
        this.koersDatum = koersDatum;
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
