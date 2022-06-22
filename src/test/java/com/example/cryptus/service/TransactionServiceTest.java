//N.B. ik heb in samenspraak met Michel besloten om een nieuwe branch te
// maken met een oude versie van de code. Daarin staan een aantal tests van
// mij die nog wel werken. Dat is gedaan nadat er te veel wijzigingen plaats
// vonden die buiten mijn invloedsfeer lagen en er telkens reparaties
// uitgevoerd moesten worden. Deze testen zijn beschreven in mijn portfolio.

package com.example.cryptus.service;

import com.example.cryptus.dto.AssetDTO;
import com.example.cryptus.dto.TransactionDTO;
import com.example.cryptus.model.*;
import com.example.cryptus.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TransactionServiceTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private BankConfigService bankConfigService;
    @Autowired
    private KoersService koersService;
    private final int TEST_ASSET_AMOUNT = 1;
    Customer testbuyer1;
    String testasset = "Bitcoin";

    public void testSetup() {
        testbuyer1 = customerRepository.findCustomerById(2).get();
        Asset asset = new Asset(1, "Bitcoin", "BTC", null, 25.0);
        Transaction transaction = new Transaction(3, null, null, 20.00, 300.00, 0.00, LocalDateTime.parse("2022-05-19T01:14:07.00"));
        Customer testbuyer2 = new Customer(2, "Rogier", "", "Mullaart",
                "admin123456", "administrator", Date.valueOf("1969-08-13"),
                "163747879", new Address(), "0647185166");
        Customer testseller1 = new Customer(3, "Frits", "", "Botersprits", "12345",
                "boter", Date.valueOf("1973-06-16"), "163647895",
                new Address(1, "1011ZH", "dam", "Amsterdam"), "0647176156");
        Portefeuille portefeuille = new Portefeuille(1, null, new ArrayList<>());
        Portefeuille portefeuille2 = new Portefeuille(2, null, new ArrayList<>());
        testbuyer2.setPortefeuille(portefeuille);
        List<Asset> assets = new ArrayList<>();
        assets.add(asset);
        portefeuille2.setAssetLijst(assets);
        BankAccount bankAccount = new BankAccount("1234567891", 1000000.00, 2);
        BankAccount bankAccount2 = new BankAccount("9876543211", 1000, 3);
        testbuyer2.setBankAccount(bankAccount);
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);
        testseller1.setPortefeuille(portefeuille2);
        testseller1.setBankAccount(bankAccount2);
        TransactionDTO transactionDTO = new TransactionDTO(0, new AssetDTO(asset), 20, 300);
    }

    @Test
    void buyFromBank() {
        testSetup();
        Transaction testtransactie = transactionService.buyFromBank(testbuyer1,
                testasset, TEST_ASSET_AMOUNT);
        String actualAssetNaam = testtransactie.getAsset().getAssetNaam();
        String expectedAssetNaam = testasset;
        assertThat(actualAssetNaam).isNotNull().isEqualTo(expectedAssetNaam);
        double actualAssetAmount = testtransactie.getAssetamount();
        assertThat(actualAssetAmount).isNotNull().isEqualTo(TEST_ASSET_AMOUNT);
    }

    @Test
    void calcValueTransactionInEuro() {
        testSetup();
        Transaction testtransactie = transactionService.buyFromBank(testbuyer1,
                testasset, TEST_ASSET_AMOUNT);
        double testkoers =
                koersService.findKoersByAssetNaam(testasset).get().getKoersInEuro();
        double actualValue = testtransactie.getEuroamount();
        double PERCENTAGE = 100;
        double expectedValue = testkoers + testkoers * (bankConfigService.getPercentage() / PERCENTAGE);
        assertThat(actualValue).isNotNull().isEqualTo(expectedValue);
    }

    @Test
    void calcFee() {
    }

    @Test
    void calTotal() {
    }

    @Test
    void findTransactionById() {
        testSetup();
        Transaction testtransactie = transactionService.buyFromBank(testbuyer1,
                testasset, TEST_ASSET_AMOUNT);
        Optional<Transaction> expected =
                transactionService.findTransactionById(testtransactie.getTransactionId());
        Optional<Transaction> actual = Optional.of(testtransactie);
        actual.get().setTimestamp(null); // Ignore timestamp
        expected.get().setTimestamp(null);
        actual.get().setFeePercentage(0.00); // Ignore percentage
        expected.get().setFeePercentage(0.00);
        assertThat(actual).isNotNull().isEqualTo(expected);
    }
}
