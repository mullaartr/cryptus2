package com.example.cryptus.repository;

import com.example.cryptus.dao.CustomerDao;
import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dao.KoopTransactieDaoJDBC;
import com.example.cryptus.dao.PortefeuilleDAO;
import com.example.cryptus.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;



class TransactionRepositoryTest {


    private KoopTransactieDaoJDBC mockKoopTransactieDaoJdbc;
    private CustomerDaoJdbc mockCustomerDAO;
    private PortefeuilleDAO mockPortefeuilleDAO;
    private BankConfigRepository mockBankConfigRepository;
    private Transaction transaction;
    private Asset asset;
    private List<Transaction> transactionList;
    private List<Transaction> transactionList2;
    private Transaction transaction1;
    private Customer customer1;
    private Portefeuille portefeuille;
    private BankAccount bankAccount;
    private Customer customer2;
    private Portefeuille portefeuille2;
    private BankAccount bankAccount2;


    @BeforeEach
    void setup(){
        asset = new Asset(1, "Bitcoin", "BTC", null, 25.0);
        transaction1 = new Transaction(3, null, null, 20.00, 300.00, 0.00, LocalDateTime.parse("2022-05-19T01:14:07.00"));
        transactionList2 = new ArrayList<>();
        transactionList2.add(transaction1);
        customer1 = new Customer(3, "Jan", "van", "Zevenaar", "zeven", "12345", Date.valueOf("1950-09-10"),"156677882",
                new Address(1,"Rokin","1001AA","Amsterdam"),"0647186543");
        portefeuille = new Portefeuille(2, null, new ArrayList<>());
        portefeuille2 = new Portefeuille(1, null, new ArrayList<>());
        customer1.setPortefeuille(portefeuille);
        bankAccount = new BankAccount("1234567891",1000000.00,1);
        bankAccount2 = new BankAccount("9934567891",100,5);
        customer1.setBankAccount(bankAccount);
        transaction = new Transaction(3, customer1, asset, 20.00, 300.00, 0.00, LocalDateTime.parse("2022-05-19T01:14:07.00"));
        transactionList = new ArrayList<>();
        transactionList.add(transaction);
        customer2 = new Customer(2, "Frits", null, "Botersprits", "boter", "12345", Date.valueOf("1973-06-16"), "163647895", new Address(1, "1011ZH", "dam", "Amsterdam"), "0647176156");
        customer2.setPortefeuille(portefeuille2);
        customer2.setBankAccount(bankAccount2);

        mockKoopTransactieDaoJdbc = Mockito.mock(KoopTransactieDaoJDBC.class);
        mockPortefeuilleDAO = Mockito.mock(PortefeuilleDAO.class);
        mockCustomerDAO = Mockito.mock(CustomerDaoJdbc.class);
        mockBankConfigRepository = Mockito.mock(BankConfigRepository.class);


        Mockito.when(mockKoopTransactieDaoJdbc.findTransactions()).thenReturn(transactionList);
        Mockito.when(mockPortefeuilleDAO.findAssetOfVerkoopTransactie(1)).thenReturn(Optional.ofNullable(asset));
        Mockito.when(mockCustomerDAO.findBuyerByTransactionId(3)).thenReturn(Optional.ofNullable(customer1));
        Mockito.when(mockBankConfigRepository.getPercentage()).thenReturn(6.00);

    }

    @Test
    void findTransactionsByUser() {
    }

    @Test
    void testFindTransactionsByUser() {
    }

    @Test
    void findKoopTransactions() {
        List<Transaction> actual = mockKoopTransactieDaoJdbc.findTransactions();
        List<Transaction> expected = transactionList;
        assertThat(actual).isNotNull().isEqualTo(expected);
    }
}