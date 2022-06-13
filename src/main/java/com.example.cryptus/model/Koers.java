package com.example.cryptus.model;

import com.example.cryptus.dto.KoersDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Koers {

    private final Logger logger = LoggerFactory.getLogger(Koers.class);

    private int id;
    private double koersInEuro;
    private double koersInDollars;
    private LocalDateTime koersDatum;
    private Asset asset;

    //todo LocalDateTime koersDatum implementeren in alle Koersklassen

    public Koers(int id, double koersInEuro, double koersInDollars, Asset asset) {
        this.id = id;
        this.koersInEuro = koersInEuro;
        this.koersInDollars = koersInDollars;
        this.asset = asset;
    }


    public Koers(double koersInEuro, double koersInDollars, LocalDateTime koersDatum, Asset asset) {
        this.koersInEuro = koersInEuro;
        this.koersInDollars = koersInDollars;
        this.asset = asset;
        this.koersDatum = koersDatum;
    }

    public Koers() {
        logger.info("Koers created with no-args constructor");
    }

    //Constructor for KoersDto
    public Koers(KoersDto koersDto) {
        this.koersInEuro = koersInEuro;
        this.koersInDollars = koersInDollars;
        this.koersDatum = koersDatum;
        this.asset = asset; //hier een Asset van maken?
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

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
