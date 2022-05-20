package com.example.cryptus.dao;

import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Portefeuille;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;
import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class PortefeuilleDAOJdbcTest {

    private PortefeuilleDAOJdbc mockPortefeuilleDaoJDBC;
    private Portefeuille portefeuille;
    private Asset asset;

    private Asset mockAsset;

    @Autowired
    public PortefeuilleDAOJdbcTest(PortefeuilleDAOJdbc portefeuilleDAOJdbc){
        super();
        mockPortefeuilleDaoJDBC = portefeuilleDAOJdbc;
    }
   /* @BeforeAll
    void setUp(){
        mockPortefeuilleDaoJDBC = Mockito.mock(PortefeuilleDAOJdbc.class);
        portefeuille = new Portefeuille(5, 1, null, new ArrayList<Asset>());
        Asset asset = new Asset();
        Mockito.when(mockPortefeuilleDaoJDBC.findPortefeuilleById(1)).thenReturn(Optional.of(portefeuille));
    }*/

    @Test
    void findPortefeuilleById() {
        Optional<Portefeuille> portefeuille1 = mockPortefeuilleDaoJDBC.findPortefeuilleById(1);
        Portefeuille actual = portefeuille1.get();
        Portefeuille expected = portefeuille;
        assertThat(actual).isNotNull().isEqualTo(expected);

    }
}