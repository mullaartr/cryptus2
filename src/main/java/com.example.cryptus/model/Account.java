package com.example.cryptus.model;

import org.mindrot.jbcrypt.BCrypt;

public class Account {

    private String gebruikersnaam;
    private String wachtwoord;
    private final String PEPPER = "iliaWavWavaZisSublisZarRvi";

    public Account(String gebruikersnaam, String wachtwoord) {
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
    }

    public Account(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public String hashSaltNPepper(String mpWachtwoord) {
        return BCrypt.hashpw(wachtwoord, BCrypt.gensalt(16) + PEPPER);
    }

    public void checkStringForHashed(String string, String hashed) {
        BCrypt.checkpw(string, hashed);
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = hashSaltNPepper(wachtwoord);
    }

}