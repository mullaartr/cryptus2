
package com.example.cryptus.service;

import com.example.cryptus.model.*;
import com.example.cryptus.repository.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionServiceTest {
    private static TransactionRepository mockTransRepo =
            Mockito.mock(TransactionRepository.class);
    private static final CustomerRepository mockCustRepo =
            Mockito.mock(CustomerRepository.class);
    private static final PortefeuilleRepository mockPortRepo =
            Mockito.mock(PortefeuilleRepository.class);
    private static final AssetRepository mockAssetRepo =
            Mockito.mock(AssetRepository.class);
    private static final BankConfigRepository mockBankConfigRepo =
            Mockito.mock(BankConfigRepository.class);
    private static final BankAccountService mockBankAccountService =
            Mockito.mock(BankAccountService.class);
    private static final KoersRepository mockKoersRepo =
            Mockito.mock(KoersRepository.class);

    @BeforeAll
    public static void testSetup() {
        Customer testbuyer = new Customer();
        Customer testseller = new Customer();

        Asset asset = new Asset(1,"bitcoin","BTC",new Koers(29000.00,28.000,
                LocalDateTime.now(),new Asset("bitcoin")));
        Transaction transaction1 = new Transaction(1,testbuyer,testseller,null,1.00,
                29000.00,15.00,LocalDateTime.now());

        TransactionService transactionServiceUnderTest =
                new TransactionService(mockTransRepo, mockCustRepo, mockPortRepo,
                        mockBankConfigRepo, mockBankAccountService, mockAssetRepo, mockKoersRepo);
    }

    @Test
    void getBuyTransactionsFromUser() {
        // maak een Customer buyer en koop een 1 crypto en laat de lijst met
        // 1 transactie zien??
//        Mockito.when(mockTransRepo.getBuyTransactionsFromUser().thenReturn(new ArrayList<>()));
//        List<Transaction> actual = transactionServiceUnderTest.list();
//        List <Transaction> expected = new ArrayList<>();
//        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void getSellTransactionsFromUser() {
        // maak een Customer buyer en koop een 1 crypto en laat nu de lijst met
        // 1 transactie zien van de verkoper??
    }

    @Test
    void buyFromBank() {
    }

    @Test
    void calcValueTransactionInEuro() {
    }

    @Test
    void calcFee() {
    }

    @Test
    void calTotal() {
    }

    @Test
    void findTransactionById() {
//        Mockito.when(mockTransRepo.findTransactionById(1)).thenReturn(Optional.of());
//        TransactionService transactionServiceUnderTest =
//                new TransactionService(mockTransRepo);
//        Optional <Transaction> actual =
//                transactionServiceUnderTest.findTransactionById(1);
//        Optional <Transaction> expected = Optional.of(transaction);
//        assertThat(actual).isNotNull().isEqualTo(expected);
    }
}