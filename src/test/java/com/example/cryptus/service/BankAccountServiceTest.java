package com.example.cryptus.service;

import com.example.cryptus.model.BankAccount;
import com.example.cryptus.repository.BankAccountRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankAccountServiceTest {

    private BankAccount bankAccount;
    private BankAccountRepository mockRepository;
    private BankAccountService serviceUnderTest;

    @BeforeAll
    void initBankAccount(){
        mockRepository = Mockito.mock(BankAccountRepository.class);
        serviceUnderTest = new BankAccountService(mockRepository);
        bankAccount = new BankAccount("NL73 INGB 123 456 78 00", 500,6);

    }



    @Test
    @DisplayName("Testing find Bankaccount by id method")
    void findCustomerById() {
        Mockito.when(mockRepository.findBankaccountByUserId(6)).thenReturn(Optional.of(bankAccount));
        BankAccountService serviceUnderTest = new BankAccountService(mockRepository);
        Optional<BankAccount> actual = serviceUnderTest.findBankaccountByUserId(6);
        Optional <BankAccount> expected = Optional.of(bankAccount);
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void store() {


        BankAccount testBankAccount = new BankAccount("NL73 INGB 123 456 78 00", 500,3);
        serviceUnderTest.store(testBankAccount);
        Mockito.when(mockRepository.findBankaccountByUserId(3)).thenReturn(Optional.of(testBankAccount));
        Optional<BankAccount> actual = serviceUnderTest.findBankaccountByUserId(3);
        Optional<BankAccount> expected = Optional.of(testBankAccount);
        assertThat(actual).isNotNull().isEqualTo(expected);
    }


    @Test
    void list() {
        BankAccountRepository mockRepository = Mockito.mock(BankAccountRepository.class);
        Mockito.when(mockRepository.list()).thenReturn(new ArrayList<>());
        BankAccountService serviceUnderTest = new BankAccountService(mockRepository);
        List<BankAccount> actual = serviceUnderTest.list();
        assertThat(actual).isNotNull();
    }

    @Test
    void delete() {

        serviceUnderTest.delete(6);
        Mockito.doNothing().when(mockRepository).delete(2);
        Optional<BankAccount> actual = serviceUnderTest.findBankaccountByUserId(2);
        assertThat(actual.isEmpty()).isTrue();
    }

    @Test
    void addFunds() {
        bankAccount = new BankAccount();
        bankAccount.setBalance(5000);
        bankAccount.addFunds(5000);
        assertEquals(10000,bankAccount.getBalance());
    }

    @Test
    void withdrawFunds() {
        bankAccount = new BankAccount();
        bankAccount.setBalance(5000);
        bankAccount.withdrawFunds(5000);
        assertEquals(0,bankAccount.getBalance());
    }
}