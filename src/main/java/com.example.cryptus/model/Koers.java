package com.example.cryptus.model;

import com.example.cryptus.dto.KoersDto;
import com.example.cryptus.model.Asset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Koers {

    private final Logger logger = LoggerFactory.getLogger(Koers.class);

    private String naam;
    private double koersInEuro;
    private double koersInDollars;
    private Asset asset;
    //later Date toevoegen?

    public Koers(String naam, double koersInEuro, double koersInDollars, Asset asset) {
        this.naam = naam;
        this.koersInEuro = koersInEuro;
        this.koersInDollars = koersInDollars;
        this.asset = asset;
    }

    public Koers(String naam, double koersInEuro, double koersInDollars) {
        this(naam, koersInEuro, koersInDollars, null);
    }

    public Koers() {
        logger.info("Koers created with no-args constructor");
    }

//    //Constructor for KoersDto
//    public Koers(KoersDto koersDto) {
//        this.koersInEuro = koersInEuro;
//        this.koersInDollars = koersInDollars;
//        this.asset = asset; //hier een Asset van maken?
//    }


    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
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

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;

    }
}
