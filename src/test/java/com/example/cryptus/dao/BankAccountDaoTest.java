package com.example.cryptus.dao;

import com.example.cryptus.model.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankAccountDaoTest {

    private BankAccountDao daoUnderTest;
    private BankAccount bankAccount;


    @Autowired
    public BankAccountDaoTest(BankAccountDao daoUnderTest) {
        this.daoUnderTest = daoUnderTest;
    }

    @BeforeEach
    void init(){
        bankAccount = new BankAccount("1234567891", 1000000.00, 1);
    }

    @Test
    void findBankAccountByUserId() {
        Optional<BankAccount> bankAccountOptional = daoUnderTest.findBankAccountByUserId(1);
        BankAccount actual = bankAccountOptional.orElse(null);
        BankAccount expected = bankAccount;
        assertEquals(expected, actual);
    }

    @Test
    void addFunds() {
    }

    @Test
    void withdrawFunds() {
    }

    @Test
    void store() {
    }

    @Test
    void list() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}