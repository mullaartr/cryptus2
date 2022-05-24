package com.example.cryptus.dao;

import com.example.cryptus.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransactionDaoJdbcTest {

    @Autowired
    private TransactionDaoJdbc transactionDaoJdbc;

    @Test
    void findTransactionsByUser() {
        System.out.println(transactionDaoJdbc.findTransactionsByUser(1));
    }
}