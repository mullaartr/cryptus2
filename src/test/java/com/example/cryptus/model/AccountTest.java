package com.example.cryptus.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {

    @BeforeEach
    void setUp() {
        Account testAccount = new Account("Zohar", "wachtwoord");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void hashSaltNPepper() {
        Account testAccount = new Account("Zohar", "wachtwoord");
        String actual = "abracadabra";
        String expected = "abracadabra";
        testAccount.hashSaltNPepper(actual);
        testAccount.hashSaltNPepper(expected);
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void getAccountId() {
    }

    @Test
    void getGebruikersnaam() {
    }

    @Test
    void getWachtwoord() {
    }

    @Test
    void getSalt() {
    }

    @Test
    void setAccountId() {
    }

    @Test
    void setGebruikersnaam() {
    }

    @Test
    void setWachtwoord() {
        Account testAccount = new Account("Zohar", "wachtwoord");
    }

    @Test
    void setSalt() {
    }
}