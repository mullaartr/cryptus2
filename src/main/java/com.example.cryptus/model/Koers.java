package com.example.cryptus.model;

public class Koers {

    private String name;
    private int koersInDollar;
    private int koersInEuro;

    public Koers(String name, int koersInDollar, int koersInEuro) {
        this.name = name;
        this.koersInDollar = koersInDollar;
        this.koersInEuro = koersInEuro;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKoersInDollar() {
        return koersInDollar;
    }

    public void setKoersInDollar(int koersInDollar) {
        this.koersInDollar = koersInDollar;
    }

    public int getKoersInEuro() {
        return koersInEuro;
    }

    public void setKoersInEuro(int koersInEuro) {
        this.koersInEuro = koersInEuro;
    }
}
