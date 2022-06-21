package com.example.cryptus.dao;

import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;

import com.example.cryptus.model.Portefeuille;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerDaoJdbcTest {

    private CustomerDaoJdbc customerDaoJdbcUnderTest;
    private CustomerDao daoUnderTest;
    Customer customer;
    Customer customer1;

    private Customer customer3;


    @Autowired
    public CustomerDaoJdbcTest(CustomerDaoJdbc customerDaoJdbc) {
        super();
        this.customerDaoJdbcUnderTest = customerDaoJdbc;

    }
    @BeforeEach
    void init(){

        customer = new Customer(28,"John","gg","mekky","password","username",Date.valueOf("2015-03-31"),"bsn",
                new Address(10,"street","10690","Utrecht"),"1234567891");

        customer1 = new Customer(3, "Jan", "van", "Zevenaar", "zeven", "12345",Date.valueOf("1950-09-10"),"156677882",
                new Address(1,"Rokin","1001AA","Amsterdam"),"0647186543");

        customer3 = new Customer(2);


    }


    @Test
    void findCustomerById() {
        Optional<Customer> oc = customerDaoJdbcUnderTest.getCustomerById(3);
        Customer actual = oc.orElse(null);
        System.out.println("helloooooooooooooo"+actual);
        System.out.println(oc);
        Customer expected = customer1;
        assertThat(actual).isEqualTo(expected);
    }



    @Test
    void findCustomerByName() {

    }

    @Test
    void list(){
        List<Customer> actual = customerDaoJdbcUnderTest.list();
        System.out.println("hellooooooooooooooooo" +actual);

        assertThat(actual).isNotNull();



    }

/*    @Test
    void findBuyerOfTransactie() {
        Customer actual= customerDaoJdbcUnderTest.findBuyerOfTransactie(1).orElse(null);
        Customer expected = customer3;
        assertThat(actual).isNotNull().isEqualTo(expected);
    }*/
}