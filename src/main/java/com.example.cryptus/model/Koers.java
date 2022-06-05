package com.example.cryptus.model;

public class Koers {

    private String name;
    private double koersInDollar;
    private double koersInEuro;

    public Koers(String name, double koersInDollar, double koersInEuro) {
        this.name = name;
        this.koersInDollar = koersInDollar;
        this.koersInEuro = koersInEuro;
    }

    public Koers() {
        this("", 0.0,0.0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKoersInDollar(double koersInDollar) {
        this.koersInDollar = koersInDollar;
    }

    public void setKoersInEuro(double koersInEuro) {
        this.koersInEuro = koersInEuro;
    }

    public double getKoersInDollar() {
        return koersInDollar;
    }

    public double getKoersInEuro() {
        return koersInEuro;
    }
}
