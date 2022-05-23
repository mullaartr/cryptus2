package com.example.cryptus.dao;

import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")

class CustomerDaoJdbcTest {

    private CustomerDao daoUnderTest;

    @Autowired
    public CustomerDaoJdbcTest(CustomerDao customerDao) {
        super();
        this.daoUnderTest = customerDao;
    }

    @Test
    void findCustomerById() {
        Optional<Customer> customer = daoUnderTest.findCustomerById(5);
        Customer actual = customer.orElse(null);
        Customer expected = new Customer(5,"John","gg","mekky","password","username", Date.valueOf("2015-03-31"),"bsn",new Address(10,"street","1000","Amsterdam"),"email","1234567891","salt");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findCustomerByName() {

    }
}