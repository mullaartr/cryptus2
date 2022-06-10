package com.example.cryptus.repository;

import com.example.cryptus.dao.BankAccountDaoJdbc;
import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.model.BankAccount;
import com.example.cryptus.model.Customer;
import com.example.cryptus.service.BankAccountService;
import com.example.cryptus.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountRepositoryTest {

    @Autowired
    BankAccountRepository bankAccountRepositoryUnderTest;

    BankAccount testAccount;
    BankAccountDaoJdbc mockDao;



    @BeforeEach
    public void initTest() {
        mockDao = Mockito.mock(BankAccountDaoJdbc.class);
        bankAccountRepositoryUnderTest = new BankAccountRepository(mockDao);
        testAccount = new BankAccount("1234567",5000,3);


    }


    @Test
    @DisplayName("Testing finding bank account by user id method")
    void findBankaccountByUserId() {

        Mockito.when(mockDao.findBankAccountByUserId(3)).thenReturn(Optional.of(testAccount));
        bankAccountRepositoryUnderTest = new BankAccountRepository(mockDao);
        Optional<BankAccount> actual = bankAccountRepositoryUnderTest.findBankaccountByUserId(3);
        Optional<BankAccount> expected = Optional.ofNullable(testAccount);
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @DisplayName("Testing creating a new bank account method")
    void store() {
        bankAccountRepositoryUnderTest.store(testAccount);
        Mockito.when(mockDao.findBankAccountByUserId(3)).thenReturn(Optional.of(testAccount));
        Optional<BankAccount> actual = bankAccountRepositoryUnderTest.findBankaccountByUserId(3);
        Optional<BankAccount> expected = Optional.of(testAccount);
        assertThat(actual).isNotNull().isEqualTo(expected);

    }

    @Test
    @DisplayName("Testing update bank account method")
    void update() {
        BankAccount actual = new BankAccount("12234",600,4);
        actual.setBalance(1000);
        bankAccountRepositoryUnderTest.update(actual);
        BankAccount expected = new BankAccount("12234",1000,4);
        assertThat(actual.getBalance()).isEqualTo(expected.getBalance());

    }

    @Test
    @DisplayName("Testing update bank account list is not empty")
    void list() {
        BankAccountRepository mockDao = Mockito.mock(BankAccountRepository.class);
        Mockito.when(mockDao.list()).thenReturn(new ArrayList<>());
        List<BankAccount> actual = bankAccountRepositoryUnderTest.list();
        List <BankAccount> expected = new ArrayList<>();
        assertThat(actual).isNotNull().isEqualTo(expected);
    }


    @Test
    @DisplayName("Testing deposit to bank account method")
    void addFunds() {
        BankAccount actual = new BankAccount("12234",600,4);
        actual.addFunds(600);
        bankAccountRepositoryUnderTest.update(actual);
        BankAccount expected = new BankAccount("12234",1200,4);
        assertThat(actual.getBalance()).isEqualTo(expected.getBalance());


    }

    @Test
    @DisplayName("Testing withdraw from bank account method")
    void withdrawFunds() {
        BankAccount actual = new BankAccount("666666",600,4);
        actual.withdrawFunds(600);
        bankAccountRepositoryUnderTest.update(actual);
        BankAccount expected = new BankAccount("666666",0,4);
        assertThat(actual.getBalance()).isEqualTo(expected.getBalance());
    }



    @Test
    @DisplayName("Testing bank account cant go below zero")
    void WithdrawBelowMinimum() {
        assertThrows(RuntimeException.class, () -> testAccount.withdrawFunds(1000000));
    }


    @Test
    @DisplayName("Testing  cannot deposit zero amount to his account")
    void cantDepositZero() {
        assertThrows(RuntimeException.class, () -> testAccount.addFunds(0));
    }
}