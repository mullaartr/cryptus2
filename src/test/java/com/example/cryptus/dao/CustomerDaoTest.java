package com.example.cryptus.dao;

import com.example.cryptus.model.Address;
import com.example.cryptus.model.Adres;
import com.example.cryptus.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CustomerDaoTest {
    private CustomerDao daoUnderTest;
    private CustomerDao customerDao;

    public CustomerDaoTest(CustomerDao customerDao) {
        super();
        this.daoUnderTest = customerDao;
    }

    @Test
    void findCustomerById() {
        Optional <Customer> customerOptional = daoUnderTest.findCustomerById(1);
        Customer actual = customerOptional.orElse(null);
        Customer expected = new Customer(3,"John","gg","mekky","'","",Date.valueOf("2015-03-31"),"",new Address(0,"","",""),"","","");
    }

    @Test
    void storeCustomer() {
        Customer customerTest = new Customer(3,"John","gg","mekky","'","",Date.valueOf("2015-03-31"),"",new Address(0,"","",""),"","","");

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

    @Test
    void findCustomerByName() {
    }

    @Test
    void findCustomerByPortefeuilleId() {
    }
}