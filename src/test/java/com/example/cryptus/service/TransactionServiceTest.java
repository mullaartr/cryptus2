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

