package com.example.cryptus.service;

import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Transaction;
import com.example.cryptus.repository.CustomerRepository;
import com.example.cryptus.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionServiceTest {

    @MockBean
    private static TransactionRepository mockTransRepo =
            Mockito.mock(TransactionRepository.class);
    private static final CustomerRepository mockCustRepo =
            Mockito.mock(CustomerRepository.class);

    TransactionService transactionServiceUnderTest =
            new TransactionService(mockTransRepo, mockCustRepo);
    static Customer testCustomer = new Customer(1, "Rogier", "", "Mullaart",
            "mullaar", "12345");

    @BeforeAll
    public static void testSetup() {

        Mockito.when(mockTransRepo.findTransactionsByUser(testCustomer)).thenReturn((List<Transaction>) new Transaction());
        new TransactionService(mockTransRepo, mockCustRepo);

    }

    @Test
    void getTransactions() {

        Mockito.when(mockTransRepo.findTransactionsByUser(testCustomer)).thenReturn((new ArrayList<>()));//volgens mij klopt dit niet
//        TransactionService transactionServiceUnderTest =
//                new TransactionService(mockTransRepo, mockCustRepo);
//        List<Transaction> actual =
//                transactionServiceUnderTest.(testCustomer);
//       List<Transaction> expected =
//               transactionServiceUnderTest.getTransactions(1);
//        assertThat(actual).isNotNull().isEqualTo(expected);






}
}