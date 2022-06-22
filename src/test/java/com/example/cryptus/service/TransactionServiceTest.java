//N.B. ik heb in samenspraak met Michel besloten om een nieuwe branch te
// maken met een oude versie van de code. Daarin staan een aantal tests van
// mij die nog wel werken. Dat is gedaan nadat er te veel wijzigingen plaats
// vonden die buiten mijn invloedsfeer lagen en er telkens reparaties
// uitgevoerd moesten worden. Deze testen zijn beschreven in mijn portfolio.


//package com.example.cryptus.service;
//
//import com.example.cryptus.model.Customer;
//import com.example.cryptus.model.Transaction;
//import com.example.cryptus.repository.AssetRepository;
//import com.example.cryptus.repository.CustomerRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//class TransactionServiceTest {
//    @Autowired
//    private CustomerRepository customerRepository;
//    //@Autowired
//    //private AssetRepository assetRepository;
//    @Autowired
//    private TransactionService transactionService;
//    @Autowired
//    private BankConfigService bankConfigService;
//    @Autowired
//    private KoersService koersService;
//    private final int TEST_ASSET_AMOUNT = 1;
//    Customer testbuyer;
//    String testasset = "Bitcoin";
//    private final double PERCENTAGE = 100;
//    public void testSetup() {
//        testbuyer = customerRepository.findCustomerById(2).get();
//    }
//    @Test
//    void getBuyTransactionsFromUser() {
//
//        Mockito.when(mockTransRepo.getBuyTransactionsFromUser().thenReturn(new ArrayList<>()));
//        List<Transaction> actual = transactionServiceUnderTest.list().size;
//        List <Transaction> expected = new ArrayList<>().size;
//        assertThat(actual).isNotNull().isEqualTo(expected);
//    }
//    @Test
//    void getSellTransactionsFromUser() {
//    }
//    @Test



//    @Test
//    void getSellTransactionsFromUser() {
//        // maak een Customer buyer en koop een 1 crypto en laat nu de lijst met
//        // 1 transactie zien van de verkoper??
//    }
    // Test waarin user 2 1 bitcoin koopt van de bank: het asset saldo van de
    // klant en bank wijzigigen daarbij op de juiste wijze en de bankrekening
    // saldo's wijzigen daarbij
//    @Test

 //   @Test

//    void buyFromBank() {
//        testSetup();
//        Transaction testtransactie = transactionService.buyFromBank(testbuyer,
//                testasset, TEST_ASSET_AMOUNT).get();
//        String actualAssetNaam = testtransactie.getAsset().getAssetNaam();
//        String expectedAssetNaam = testasset;
//        assertThat(actualAssetNaam).isNotNull().isEqualTo(expectedAssetNaam);
//        double actualAssetAmount = testtransactie.getAssetamount();
//        double expectedAssetAmount = TEST_ASSET_AMOUNT;
//        assertThat(actualAssetAmount).isNotNull().isEqualTo(expectedAssetAmount);
//    }
//    @Test
//    void calcValueTransactionInEuro() {
//        testSetup();
//        Transaction testtransactie = transactionService.buyFromBank(testbuyer,
//                testasset, TEST_ASSET_AMOUNT).get();
//        double testkoers =
//                koersService.findKoersByAssetNaam(testasset).get().getKoersInEuro();
//        double actualValue = testtransactie.getEuroamount();
//        double expectedValue = testkoers + testkoers * (bankConfigService.getPercentage()/PERCENTAGE);
//        assertThat(actualValue).isNotNull().isEqualTo(expectedValue);
//    }
//
//    @Test
//    void calcFee() {
//    }
//
//    @Test
//    void calTotal() {
//    }


//    @Test
//    void calcFee() {
//    }
//    @Test
//    void calTotal() {
//    }

//    @Test
//    void findTransactionById() {
//        testSetup();
//        Transaction testtransactie = transactionService.buyFromBank(testbuyer,
//                testasset, TEST_ASSET_AMOUNT).get();
//        Optional<Transaction> expected =
//                transactionService.findTransactionById(testtransactie.getTransactionId());
//        Optional <Transaction> actual = Optional.of(testtransactie);
//        actual.get().setTimestamp( null ); // Ignore timestamp
//        expected.get().setTimestamp( null );
//        actual.get().setFeePercentage( 0.00 ); // Ignore percentage
//        expected.get().setFeePercentage( 0.00 );
//        assertThat(actual).isNotNull().isEqualTo(expected);
//    }

//}

//}

package com.example.cryptus.service;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dao.KoopTransactieDaoJDBC;
import com.example.cryptus.dao.PortefeuilleDAO;
import com.example.cryptus.dto.AssetDTO;
import com.example.cryptus.dto.TransactionDTO;
import com.example.cryptus.model.*;
import com.example.cryptus.repository.BankConfigRepository;
import com.example.cryptus.repository.TransactionRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionServiceTest {
    private Transaction transaction;
    private Asset asset;
    private List<Transaction> transactionList;
    private TransactionDTO transactionDTO;
    private List<Asset> assets;
    private Transaction transaction1;
    private Customer customer1;
    private Portefeuille portefeuille;
    private BankAccount bankAccount;
    private Customer customer2;
    private Portefeuille portefeuille2;
    private BankAccount bankAccount2;

    private final TransactionService transactionServiceUnderTest;

    @Autowired
    public TransactionServiceTest(TransactionService transactionService){
        super();
        transactionServiceUnderTest = transactionService;
    }

    @BeforeEach
    void setup(){
        asset = new Asset(1, "Bitcoin", "BTC", null, 25.0);
        transaction1 = new Transaction(3, null, null, 20.00, 300.00, 0.00, LocalDateTime.parse("2022-05-19T01:14:07.00"));
        customer1 = new Customer(3, "Jan", "van", "Zevenaar", "zeven", "12345", Date.valueOf("1950-09-10"),"156677882",
                new Address(1,"Rokin","1001AA","Amsterdam"),"0647186543");
        portefeuille = new Portefeuille(1, null, new ArrayList<>());
        portefeuille2 = new Portefeuille(2, null, new ArrayList<>());
        customer1.setPortefeuille(portefeuille);
        assets = new ArrayList<>();
        assets.add(asset);
        portefeuille2.setAssetLijst(assets);
        bankAccount = new BankAccount("1234567891",1000000.00,1);
        bankAccount2 = new BankAccount("9876543211",1000,2);
        customer1.setBankAccount(bankAccount);
        transactionList = new ArrayList<>();
        transactionList.add(transaction);
        transactionList.add(transaction1);
        customer2 = new Customer(2, "Frits", null, "Botersprits", "boter", "12345", Date.valueOf("1973-06-16"), "163647895", new Address(1, "1011ZH", "dam", "Amsterdam"), "0647176156");
        customer2.setPortefeuille(portefeuille2);
        customer2.setBankAccount(bankAccount2);
        transactionDTO = new TransactionDTO(0, new AssetDTO(asset), 20, 300);


    }

    @Test
    void doSale() {
        Transaction actual = transactionServiceUnderTest.maakKoopTransactie(transactionDTO, customer2);
        Transaction expected = null;
    }
}



