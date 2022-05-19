package com.example.cryptus.service;

import com.example.cryptus.model.Customer;
import com.example.cryptus.model.User;
import com.example.cryptus.repository.CustomerRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import static org.assertj.core.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {
    private CustomerRepository mockRepository;
    private Customer customer;
    CustomerService serviceUnderTest;


    @BeforeAll
    void initCustomer (){
        mockRepository = Mockito.mock(CustomerRepository.class);
        customer = new Customer(2,"John","gg","mekky", Date.valueOf("2015-03-31"), "", "",0,"","","",0,"","","");
        serviceUnderTest = new CustomerService(mockRepository);


    }


    @Test
    void storeCustomer() {
        Customer testCustomer = new Customer(3,"Huub","gg","mekky", Date.valueOf("2015-03-31"), "", "",0,"","","",0,"","","");
        serviceUnderTest.storeCustomer(testCustomer);
        Mockito.when(mockRepository.findCustomerById(3)).thenReturn(Optional.of(testCustomer));
        Optional<Customer> actual = serviceUnderTest.findCustomerById(3);
        Optional<Customer> expected = Optional.of(testCustomer);
        assertThat(actual).isNotNull().isEqualTo(expected);

    }

    @Test
    void update() {



    }
    @Test
    void delete() {
        //Customer testCustomer = new Customer(3,"Huub","gg","mekky", Date.valueOf("2015-03-31"), 3 ,"Amsterdam");
        serviceUnderTest.delete(2);
        Mockito.doNothing().when(mockRepository).delete(2);
        Optional<Customer> actual = serviceUnderTest.findCustomerById(2);
        assertThat(actual.isEmpty()).isTrue();
        //assertThat(actual).isEqualTo(Optional.ofNullable(null));

    }



    @Test
    @DisplayName("Testing find customer by name method")
    void findCustomerByName() {
        Mockito.when(mockRepository.findCustomerByName("John")).thenReturn(Optional.of(customer));
        Optional<Customer> actual = serviceUnderTest.findCustomerByName("John");
        Optional<Customer> expected = Optional.of(customer);
        assertThat(actual).isNotNull().isEqualTo(expected);

    }

    @Test
    @DisplayName("Testing find customer by id method")
    void findCustomerById() {
        Mockito.when(mockRepository.findCustomerById(2)).thenReturn(Optional.of(customer));
        CustomerService serviceUnderTest = new CustomerService(mockRepository);
        Optional <Customer> actual = serviceUnderTest.findCustomerById(2);
        Optional <Customer> expected = Optional.of(customer);
        assertThat(actual).isNotNull().isEqualTo(expected);

    }


    @Test
    @DisplayName("Testing Customer list is not empty")
    void list(){
        CustomerRepository mockRepository = Mockito.mock(CustomerRepository.class);
        Mockito.when(mockRepository.list()).thenReturn(new ArrayList<>());
        CustomerService serviceUnderTest = new CustomerService(mockRepository);
        List<Customer> actual = serviceUnderTest.list();
        List <Customer> expected = new ArrayList<>();
        assertThat(actual).isNotNull();


    }





}