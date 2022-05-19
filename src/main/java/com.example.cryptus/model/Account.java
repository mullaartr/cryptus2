package com.example.cryptus.model;

import org.mindrot.jbcrypt.BCrypt;

public class Account {

    private int accountId;
    private String gebruikersnaam;
    private String wachtwoord;
    private final String PEPPER = "iliaWavWavaZisSublisZarRvi";

    public Account(int accountId, String gebruikersnaam, String wachtwoord) {
        this.accountId = accountId;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
    }

    public String hashSaltNPepper(String mpWachtwoord) {
        return BCrypt.hashpw(wachtwoord, BCrypt.gensalt(16) + PEPPER);
    }

    public int getAccountId() {
        return accountId;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = hashSaltNPepper(wachtwoord);
    }

}
