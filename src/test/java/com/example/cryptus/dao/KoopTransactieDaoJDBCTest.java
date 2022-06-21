package com.example.cryptus.dao;

import com.example.cryptus.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;


import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KoopTransactieDaoJDBCTest {

    private KoopTransactieDaoJDBC koopTransactieDaoJDBCUnderTest;
    private Transaction transaction;
    private Asset asset;
    private List<Transaction> transactionList;
    private Transaction transaction1;
    private Customer customer1;
    private Portefeuille portefeuille;
    private BankAccount bankAccount;
    private Customer customer2;
    private Portefeuille portefeuille2;
    private BankAccount bankAccount2;


    @Autowired
    public KoopTransactieDaoJDBCTest(KoopTransactieDaoJDBC koopTransactieDaoJDBC){
        super();
        koopTransactieDaoJDBCUnderTest = koopTransactieDaoJDBC;
    }

    @BeforeEach
    void setup(){
        asset = new Asset(1, "Bitcoin", "BTC", null, 25.0);
        transaction = new Transaction(3, null, null, 20.00, 300.00, 0.00, LocalDateTime.parse("2022-05-19T01:14:07.00"));
        customer1 = new Customer(3, "Jan", "van", "Zevenaar", "zeven", "12345", Date.valueOf("1950-09-10"),"156677882",
                new Address(1,"Rokin","1001AA","Amsterdam"),"harry.kreeft@lumc.nl","0647186543");
        portefeuille = new Portefeuille(2, null, new ArrayList<>());
        portefeuille2 = new Portefeuille(1, null, new ArrayList<>());
        customer1.setPortefeuille(portefeuille);
        bankAccount = new BankAccount("1234567891",1000000.00,1);
        bankAccount2 = new BankAccount("9934567891",100,5);
        customer1.setBankAccount(bankAccount);
        transaction1 = new Transaction(3, customer1, asset, 20.00, 300.00, 0.00, LocalDateTime.parse("2022-05-19T01:14:07.00"));
        transactionList = new ArrayList<>();
        transactionList.add(transaction);
        customer2 = new Customer(2, "Frits", null, "Botersprits", "boter", "12345", Date.valueOf("1973-06-16"), "163647895", new Address(1, "1011ZH", "dam", "Amsterdam"), "0647176156","joop.jansen@knp.nl");
        customer2.setPortefeuille(portefeuille2);
        customer2.setBankAccount(bankAccount2);
    }

    @Order(1)
    @Test
    void findTransactions() {
        List<Transaction> actual = koopTransactieDaoJDBCUnderTest.findTransactions();
        List<Transaction> expected = transactionList;
        assertThat(actual).isNotNull().isEqualTo(expected);

    }

    @Order(2)
    @Test
    void createTransaction() {
        int actual = koopTransactieDaoJDBCUnderTest.createTransaction(transaction1);
        transaction.setTransactionId(actual);
        Transaction id = koopTransactieDaoJDBCUnderTest.findTransactionById(actual).orElse(null);
        int expected = id.getTransactionId();
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Order(3)
    @Test
    void update() {
        transaction1.setSeller(customer2);
        koopTransactieDaoJDBCUnderTest.update(transaction1);

    }
}