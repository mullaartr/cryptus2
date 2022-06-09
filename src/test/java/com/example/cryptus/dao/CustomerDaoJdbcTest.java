package com.example.cryptus.dao;

import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.sql.Date;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerDaoJdbcTest {

    private CustomerDaoJdbc customerDaoJdbcUnderTest;
    private CustomerDao daoUnderTest;
    Customer customer;


    @Autowired
    public CustomerDaoJdbcTest(CustomerDao customerDao) {
        super();
        this.daoUnderTest = customerDao;

    }
    @BeforeAll
    void init(){

        customer = new Customer(28,"John","gg","mekky","password","username",Date.valueOf("2015-03-31"),"bsn",
                new Address(10,"street","10690","Utrecht"),"seb@seb.com","1234567891");

    }


    @Test
    void findCustomerById() {
        Optional<Customer> oc = daoUnderTest.findCustomerById(1);
        Customer actual = oc.orElse(null);
        Customer expected = customer;
        assertThat(actual).isEqualTo(expected);
    }



    @Test
    void findCustomerByName() {

    }
}