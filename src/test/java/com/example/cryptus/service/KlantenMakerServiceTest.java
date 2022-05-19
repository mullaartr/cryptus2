package com.example.cryptus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class KlantenMakerServiceTest {
    LocalDate vroegsteDatum;
    LocalDate laatsteDatum;
    KlantenMakerService klantenMakerService;
    @BeforeEach
    void setup(){
        vroegsteDatum = LocalDate.of(1920, 1, 1);
        laatsteDatum = LocalDate.of(2002, 1, 1);
        klantenMakerService = new KlantenMakerService();
    }

    @Test
    void maakDatum() {
        LocalDate randomDatum = klantenMakerService.maakDatum(vroegsteDatum, laatsteDatum);
        assertThat(randomDatum).isNotNull();
    }

    @Test
    void genereerPostcode(){
        String postcode = klantenMakerService.genereerPostcode();
        assertThat(postcode).isNotNull();
    }
}