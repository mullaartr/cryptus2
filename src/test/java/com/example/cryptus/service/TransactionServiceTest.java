package com.example.cryptus.service;

import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Transaction;
import com.example.cryptus.repository.CustomerRepository;
import com.example.cryptus.repository.PortefeuilleRepository;
import com.example.cryptus.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionServiceTest {

    @MockBean
    private static TransactionRepository mockTransRepo =
            Mockito.mock(TransactionRepository.class);
    private static final CustomerRepository mockCustRepo =
            Mockito.mock(CustomerRepository.class);
    private static final PortefeuilleRepository mockPortRepo =
            Mockito.mock(PortefeuilleRepository.class);

    TransactionService transactionServiceUnderTest =
            new TransactionService(mockTransRepo, mockCustRepo);
    static Customer testCustomer = new Customer(1, "Rogier", "", "Mullaart",
            "mullaar", "12345");

    @BeforeAll
    public static void testSetup() {

        Mockito.when(mockTransRepo.getBuyTransactionsFromUser(testCustomer.getUserId())).thenReturn((List<Transaction>) new Transaction());
        new TransactionService(mockTransRepo, mockCustRepo);

    }
//    @Test
//    void getBuyTransactions() {
//
//        TransactionService transactionServiceUnderTest =
//                new TransactionService(mockTransRepo, mockCustRepo, mockPortRepo);
//        List<Transaction> actual =
//                transactionServiceUnderTest.getBuyTransactionsFromUser(1);
//        List<Transaction> expected =
//                transactionServiceUnderTest.;
//        assertThat(actual).isNotNull().isEqualTo(expected);
//    }


}