package com.example.cryptus.dao;

import com.example.cryptus.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;

import java.lang.ref.Cleaner;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@Sql({"/schema.sql","/Mekky'sData.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PortefeuilleDAOJdbcTest {

    private PortefeuilleDAOJdbc portefeuilleDaoJDBCUnderTest;
    private Portefeuille portefeuille;
    private Portefeuille portefeuille1;
    private Portefeuille portefeuille4;
    private List<Portefeuille> portefeuilles;
    private Customer mullaart;
    private CustomerDaoJdbc customerDaoJdbc;
    private Asset asset;
    private Asset asset1;
    private Asset asset2;
    private Asset asset3;
    private Asset asset5;
    private List<Asset> assetList;
    private List<Asset> assetList1;
    private List<Asset> assetList3;

    @Autowired
    public PortefeuilleDAOJdbcTest(PortefeuilleDAOJdbc portefeuilleDAOJdbc){
        super();
        portefeuilleDaoJDBCUnderTest = portefeuilleDAOJdbc;
    }

    @BeforeAll
    void setUp(){
        assetList = new ArrayList<>();
        asset = new Asset(1, "Bitcoin", "BTC", 0.0, 25.0);
        assetList.add(asset);
        portefeuille = new Portefeuille(1, null, assetList);
        mullaart = new Customer(1,"Rogier",null,"Mullaart","12345","12345", Date.valueOf("1969-08-13"),"163647861",
                new Address(6,"Justine de Gouwerhof","2011GP","Haarlem")
                ,"rogier.mullaart@gmail.com","0647185165");
        portefeuille1 = new Portefeuille(3, mullaart, new ArrayList<>());
        asset1 = new Asset(1, "Bitcoin", "BTC", 0.0, 4.0);
        asset2 = new Asset(2, "Etherium", "ETH", 0.0, 8.0);
        asset3 = new Asset(3, "Dodgecoin", "DGC", 0.0,  8.0);
        asset5 = new Asset(2, "Etherium", "ETH", 0.0, 25.0);
        assetList3 = new ArrayList<>();
        assetList3.add(asset5);
        portefeuille4 = new Portefeuille(2, null, assetList3);
        portefeuilles = new ArrayList<>();
        portefeuilles.add(portefeuille);
        portefeuilles.add(portefeuille4);
        assetList1 = new ArrayList<>();
        assetList1.add(asset1);
        assetList1.add(asset2);
        assetList1.add(asset3);
        portefeuille1.setAssets(assetList1);
        customerDaoJdbc = new CustomerDaoJdbc(new JdbcTemplate());
    }


    @Test
    @Order(1)
    void findPortefeuilleById() {
        Optional<Portefeuille> portefeuille2 = portefeuilleDaoJDBCUnderTest.findPortefeuilleById(1);
        Portefeuille actual = portefeuille2.get();
        Portefeuille expected = portefeuille;
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @Order(2)
    void findAll(){
        List<Portefeuille> actual = portefeuilleDaoJDBCUnderTest.findPortefeuilles().orElse(null);
        List<Portefeuille> expected = portefeuilles;
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @Order(3)
    void storePortefeuille(){
        portefeuilleDaoJDBCUnderTest.store(portefeuille1);
        Portefeuille actual = portefeuilleDaoJDBCUnderTest.findPortefeuilleById(3).orElse(null);
        //portefeuille1.setOwner(customerDaoJdbc.findCustomerByPortefeuilleId(portefeuille1.getPortefeuilleId()).orElse(null));
        Portefeuille expected = portefeuille1;
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @Order(4)
    void updatePortefeuille() {
        portefeuille1.setPortefeuilleId(4);
        portefeuilleDaoJDBCUnderTest.store(portefeuille1);
        Asset asset = portefeuille1.getAssets().
                stream().filter(asset1 -> asset1.getAssetNaam() == "Bitcoin").
                findAny().orElse(null);
        asset.setSaldo(10);
        portefeuilleDaoJDBCUnderTest.update(portefeuille1, "Bitcoin");
        Portefeuille actual = portefeuilleDaoJDBCUnderTest.findPortefeuilleById(4).orElse(null);
        assertThat(actual).isNotNull().isEqualTo(portefeuille1);
    }

    @Test
    @Order(5)
    void deletePortefeuille(){
        portefeuilleDaoJDBCUnderTest.delete(portefeuille1.getPortefeuilleId());
        assertThat(portefeuilleDaoJDBCUnderTest.findPortefeuilleById(4).orElse(null)).isEqualTo(new Portefeuille());
    }
}
