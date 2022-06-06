package com.example.cryptus.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PortefeuilleTest {
    private Portefeuille portefeuille;

    private Portefeuille portefeuille1;
    private Asset asset;

    private Asset asset2;

    private Map<Asset, Double> assetList;
    private Map<Asset, Double> assetList1;

    @BeforeEach
    void setUp(){
        assetList = new HashMap<>();
        assetList1 = new HashMap<>();
        portefeuille = new Portefeuille(1, null, assetList);
        portefeuille1 = new Portefeuille(1, null, assetList1);
        asset = new Asset(1, "Bitcoin", "BTC", 0.0);
        asset2 = new Asset(1, "Bitcoin", "BTC", 0.0);
        assetList.put(asset, 25.0);
        assetList1.put(asset2, 15.0);

    }

    @Test
    void checkVoorSaldoEnPasAan() {
        assertTrue(portefeuille.checkVoorSaldoEnPasAan("Bitcoin", 10.0));
        Portefeuille expected = portefeuille1;
        Portefeuille actual = portefeuille;
        assertThat(actual).isNotNull().isEqualTo(expected);
    }
}