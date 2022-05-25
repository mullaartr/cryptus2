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

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
//@Sql({"/schema.sql","/data.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PortefeuilleDAOJdbcTest {

    private PortefeuilleDAOJdbc portefeuilleDaoJDBCUnderTest;
    private Portefeuille portefeuille;
    private Portefeuille portefeuille1;

    private List<Asset> assetList1;
    private Customer mullaart;
    private CustomerDaoJdbc customerDaoJdbc;

    @Autowired
    public PortefeuilleDAOJdbcTest(PortefeuilleDAOJdbc portefeuilleDAOJdbc){
        super();
        portefeuilleDaoJDBCUnderTest = portefeuilleDAOJdbc;
    }
    @BeforeAll
    void setUp(){
        List<Asset> assetList = new ArrayList<>();
        Asset asset = new Asset(1, "Bitcoin", "BTC", 0.0, null, 25.0);
        assetList.add(asset);
        portefeuille = new Portefeuille(1, null, assetList);
        mullaart = new Customer(1,"Rogier",null,"Mullaart","12345","12345", Date.valueOf("1969-08-13"),"163647861",new Address(6,"Justine de Gouwerhof","2011GP","Haarlem"),"rogier.mullaart@gmail.com","0647185165");
        portefeuille1 = new Portefeuille(3, mullaart, new ArrayList<>());
        Asset asset1 = new Asset(1, "Bitcoin", "BTC", 0.0, null , 4.0);
        Asset asset2 = new Asset(2, "Etherium", "ETH", 0.0, null, 8.0);
        Asset asset3 = new Asset(3, "Dodgecoin", "DGC", 0.0, null, 8.0);
        assetList1 = new ArrayList<>();
        assetList1.add(asset1);
        assetList1.add(asset2);
        assetList1.add(asset3);
        portefeuille1.setAssets(assetList1);
        customerDaoJdbc = new CustomerDaoJdbc(new JdbcTemplate());


    }

    @Test
    void findPortefeuilleById() {
        Optional<Portefeuille> portefeuille2 = portefeuilleDaoJDBCUnderTest.findPortefeuilleById(1);
        Portefeuille actual = portefeuille2.get();
        Portefeuille expected = portefeuille;
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void storePortefeuille(){
        portefeuilleDaoJDBCUnderTest.store(portefeuille1);
        for (int i = 0; i < assetList1.size(); i++) {
            portefeuilleDaoJDBCUnderTest.storePortefeuilleRegel(portefeuille1, portefeuille1.getAssets().get(i));
        }
        Portefeuille actual = portefeuilleDaoJDBCUnderTest.findPortefeuilleById(3).orElse(null);
        //portefeuille1.setOwner(customerDaoJdbc.findCustomerByPortefeuilleId(portefeuille1.getPortefeuilleId()).orElse(null));
        Portefeuille expected = portefeuille1;
        assertThat(actual).isNotNull().isEqualTo(expected);


    }
}