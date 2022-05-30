package com.example.cryptus.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {


    @BeforeEach
    void setUp() {

    }

    @Test
    void calcCommission() {
        Transaction testTransaction = new Transaction(0,new Customer(),
                new Customer(),new Asset(),2.5,75000.50,15, LocalDateTime.now());
        double actual = 11250.074999999999;
        double expected = testTransaction.calcCommission();
        assertThat(actual).isNotNull().isEqualTo(expected);

    }
}