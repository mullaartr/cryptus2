package com.example.cryptus.dao;

import com.example.cryptus.model.Asset;
import com.example.cryptus.model.BankAccount;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;
import com.example.cryptus.repository.BankAccountRepository;
import com.example.cryptus.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankAccountDaoJdbcTest {

    private BankAccountDaoJdbc bankAccountDaoJdbcUnderTest;
    BankAccount bankAccount;
    BankAccountDaoJdbc mockDao;
    Customer customer;

    @Autowired
    public BankAccountDaoJdbcTest(BankAccountDaoJdbc bankAccountDaoJdbc) {
        super();
        this.bankAccountDaoJdbcUnderTest = bankAccountDaoJdbc;
    }

    @BeforeEach
    void init(){
        bankAccount = new BankAccount("1234567891",1000000.00,1);

        mockDao = Mockito.mock(BankAccountDaoJdbc.class);

    }

    @Test
    void findBankAccountByUserId() {
        Optional<BankAccount> ba = bankAccountDaoJdbcUnderTest.findBankAccountByUserId(1);
        BankAccount actual = ba.orElse(null);
        System.out.println(ba);
        BankAccount expected = bankAccount;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void addFunds() {

        bankAccountDaoJdbcUnderTest.addFunds(1000000.00,1);
        bankAccount.depositMoney(1000000.00);
        System.out.println(bankAccount.getBalance());
        assertThat(bankAccount.getBalance()).isEqualTo(2000000.00);


    }

    @Test
    void withdrawFunds() {
        bankAccountDaoJdbcUnderTest.withdrawFunds(1000000.00,1);
        bankAccount.withdrawFunds(1000000.00);
        System.out.println(bankAccount.getBalance());
        assertThat(bankAccount.getBalance()).isEqualTo(0);
    }

    @Test
    void store() {
        bankAccount = new BankAccount("9934567891",100,5);

        bankAccountDaoJdbcUnderTest.store(bankAccount);
        BankAccount actual = bankAccountDaoJdbcUnderTest.findBankAccountByUserId(5).orElse(null);
        BankAccount expected = bankAccount;
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void list() {
        List<BankAccount> actual = bankAccountDaoJdbcUnderTest.list();
        assertThat(actual).isNotEmpty();

    }

    @Test
    void update() {

        bankAccount = new BankAccount("333334567891",0,5);
        bankAccount.setBalance(1000);
        bankAccountDaoJdbcUnderTest.update(bankAccount);
        assertThat(bankAccount.getBalance()).isEqualTo(1000);
    }

    @Test
    void delete() {
        bankAccount = new BankAccount("NL111111111",100,100);
        bankAccountDaoJdbcUnderTest.delete("NL111111111");
        assertThat(bankAccountDaoJdbcUnderTest.findBankAccountByUserId(100).orElse(null)).isNull();


    }
}