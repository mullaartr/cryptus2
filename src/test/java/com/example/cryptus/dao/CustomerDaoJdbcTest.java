package com.example.cryptus.dao;

import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerDaoJdbcTest {

    private CustomerDaoJdbc customerDaoJdbcUnderTest;
    private CustomerDaoJdbc customerDaoJdbc;
    Customer customer1;

    @Autowired
    public CustomerDaoJdbcTest(CustomerDaoJdbc customerDaoJdbcUnderTest) {
        super();
        customerDaoJdbcUnderTest = customerDaoJdbcUnderTest;
    }

    @BeforeAll
    void setup(){
/*
        customer1 = new Customer(12, "Klaas", "", "Fransen", "frans", "12345", Date.valueOf("1969-08-13"),"534543533",new Address(3,"Justine de Gouwerhof","2010AP","Leiden"),"harry.kreeft@lumc.nl","0647186543");
*/
        customerDaoJdbc = new CustomerDaoJdbc(new JdbcTemplate());

    }

    @Test
    void findCustomerById() {
        Optional<Customer> customer = customerDaoJdbcUnderTest.findCustomerById(2);
        Customer actual = customer.get();
        Customer expected = customer1;
        assertThat(actual).isEqualTo(expected);
    }



    @Test
    void findCustomerByName() {

    }
}