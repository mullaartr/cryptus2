package com.example.cryptus.dao;

import com.example.cryptus.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.ref.Cleaner;
import java.sql.Date;
import java.util.*;

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
    private CustomerDaoJdbc customerDaoJdbcUnderTest;
    private Asset asset;
    private Asset asset1;
    private Asset asset2;
    private Asset asset3;
    private Asset asset5;
    private Map<Asset, Double> assetList;
    private Map<Asset, Double> assetList1;
    private Map<Asset, Double> assetList3;

    @Autowired
    public PortefeuilleDAOJdbcTest(PortefeuilleDAOJdbc portefeuilleDAOJdbc, CustomerDaoJdbc customerDaoJdbc){
        super();
        portefeuilleDaoJDBCUnderTest = portefeuilleDAOJdbc;
        customerDaoJdbcUnderTest= customerDaoJdbc;
    }

    @BeforeAll
    void setUp(){
        assetList = new HashMap<>();
        asset = new Asset(1, "Bitcoin", "BTC", 0.0);
        assetList.put(asset, 25.0);
        portefeuille = new Portefeuille(1, null, assetList);
        mullaart = new Customer(1,"Rogier","","Mullaart","12345","12345", Date.valueOf("1969-08-13"),"163647861",
                new Address(6,"Justine de Gouwerhof","2011GP","Haarlem")
                ,"rogier.mullaart@gmail.com","0647185165");
        portefeuille1 = new Portefeuille(3, mullaart, new HashMap<>());
        asset1 = new Asset(1, "Bitcoin", "BTC", 0.0);
        asset2 = new Asset(2, "Etherium", "ETH", 0.0);
        asset3 = new Asset(3, "Dodgecoin", "DGC", 0.0);
        asset5 = new Asset(2, "Etherium", "ETH", 0.0);
        assetList3 = new HashMap<>();
        assetList3.put(asset5, 25.0);
        portefeuille4 = new Portefeuille(2, null, assetList3);
        portefeuilles = new ArrayList<>();
        portefeuilles.add(portefeuille);
        portefeuilles.add(portefeuille4);
        assetList1 = new HashMap<>();
        assetList1.put(asset1, 4.0);
        assetList1.put(asset2, 8.0);
        assetList1.put(asset3, 8.0);
        portefeuille1.setAssetLijst(assetList1);

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
        actual.setOwner(mullaart);
        Portefeuille expected = portefeuille1;
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @Order(4)
    void updatePortefeuille() {
        portefeuille1.setPortefeuilleId(4);
        portefeuilleDaoJDBCUnderTest.store(portefeuille1);
        for (Map.Entry<Asset, Double> entry: portefeuille1.getAssetLijst().entrySet()) {
            if(entry.getKey().getAssetNaam().equals("Bitcoin")){
                entry.setValue(10.0);
                portefeuilleDaoJDBCUnderTest.update(portefeuille1, 10, entry.getKey());

            }
        }

        Portefeuille actual = portefeuilleDaoJDBCUnderTest.findPortefeuilleById(4).orElse(null);
        actual.setOwner(mullaart);
        assertThat(actual).isNotNull().isEqualTo(portefeuille1);
    }

    @Test
    @Order(5)
    void deletePortefeuille(){
        portefeuilleDaoJDBCUnderTest.delete(portefeuille1.getPortefeuilleId());
        assertThat(portefeuilleDaoJDBCUnderTest.findPortefeuilleById(4).orElse(null)).isNull();


    }


}
