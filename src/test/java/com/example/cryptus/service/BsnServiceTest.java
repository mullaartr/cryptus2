package com.example.cryptus.service;

import org.iban4j.Iban;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BsnServiceTest {

    private String bsn1;
    private String bsn2;
    private String bsn3;
    private String bsn4;
    private String bsn5;
    private String bsn6;
    private String bsn7;
    private String bsn8;
    private String bsn9;
    private String bsn10;
    private String bsn11;
    private String bsn12;
    private String bsn13;
    private String bsn14;
    private String bsn15;
    private String bsn16;
    private List<String> bsns;
    private List<String> bsns2;
    private BsnService bsnService;
    private IbanService ibanService;

    @BeforeAll
    void setup(){
        bsn1 = "884559269";
        bsn2 = "978127596";
        bsn3 = "374592585";
        bsn4 = "576691884";
        bsn5 = "778110436";
        bsn6 = "584563309";
        bsn7 = "954980906";
        bsn8 = "588684697";
        bsn9 = "371949907";
        bsn10 = "781360109";
        bsn11 = "646068167";
        bsns = new ArrayList<>();
        bsns.add(bsn1);
        bsns.add(bsn2);
        bsns.add(bsn3);
        bsns.add(bsn4);
        bsns.add(bsn5);
        bsns.add(bsn6);
        bsns.add(bsn7);
        bsns.add(bsn8);
        bsns.add(bsn9);
        bsns.add(bsn10);
        bsns.add(bsn11);
        bsnService = new BsnService();
        bsn12 = "978127590";
        bsn13 = "374592580";
        bsn14 = "576691880";
        bsn15 = "778110430";
        bsn16 = "584563300";
        bsns2 = new ArrayList<>();
        bsns2.add(bsn12);
        bsns2.add(bsn13);
        bsns2.add(bsn14);
        bsns2.add(bsn15);
        bsns2.add(bsn16);
        ibanService = new IbanService();

    }

    @Test
    void isBsn() {
        for (int i = 0; i < bsns.size(); i++) {
            assertTrue(bsnService.isBsn(bsns.get(i)));
        }
    }

    @Test
    void isBSN(){
        for (int i = 0; i < bsns2.size(); i++) {
            assertFalse(bsnService.isBsn(bsns2.get(i)));
        }
    }

    @Test
    void isIban(){
        String iban = ibanService.ibanGenerator();
        assertThat(iban).isNotNull();
    }
}