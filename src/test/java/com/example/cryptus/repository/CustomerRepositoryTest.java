package com.example.cryptus.repository;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;
import com.example.cryptus.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;


class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepositoryUnderTest;
    Customer testCustomer;
    //CustomerDao mockDao;
    CustomerDaoJdbc mockDao;


    @BeforeEach
    public void initTest(){
        mockDao = Mockito.mock(CustomerDaoJdbc.class);
        customerRepositoryUnderTest = new CustomerRepository(mockDao);


        testCustomer = new Customer(3,"John","gg","mekky","password","username"
                , Date.valueOf("2015-03-31"),"",new Address(0,"","",""),"email","");

    }



    @Test
    @DisplayName("Testing find customer by ID method")
    void findCustomerById() {

        Mockito.when(mockDao.findCustomerById(3)).thenReturn(Optional.of(testCustomer));
        customerRepositoryUnderTest = new CustomerRepository(mockDao);
        Optional<Customer> actual = customerRepositoryUnderTest.findCustomerById(3);

        Optional<Customer> expected = Optional.ofNullable(testCustomer);
        assertThat(actual).isNotNull().isEqualTo(expected);



    }

    @Test
    @DisplayName("Testing store customer method")
    void storeCustomer() {
        customerRepositoryUnderTest.storeCustomer(testCustomer);
        Mockito.when(mockDao.findCustomerById(3)).thenReturn(Optional.of(testCustomer));
        Optional<Customer> actual = customerRepositoryUnderTest.findCustomerById(3);
        Optional<Customer> expected = Optional.of(testCustomer);
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @DisplayName("Testing update customer method")
    void update() {
        Customer actual = new Customer(11,"John","gg","mekky","'","",
                Date.valueOf("2015-03-31"),"",new Address(0,"","",""),"","122");
        actual.setLastName("James");
        customerRepositoryUnderTest.update(actual);
        Customer expected = new Customer(11,"John","gg","James","'","",
                Date.valueOf("2015-03-31"),"",new Address(0,"","",""),"","122");

        System.out.println(actual);

        assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
    }

    @Test
    @DisplayName("Testing find customer by Username method")
    void findCustomerByUsernamePassword() {
        Mockito.when(mockDao.findCustomerByUsernamePassword("username")).thenReturn(Optional.of(testCustomer));
        Optional<Customer> actual = customerRepositoryUnderTest.findCustomerByUsernamePassword("username");
        Optional<Customer> expected = Optional.of(testCustomer);
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @DisplayName("Testing find customer by name method")
    void findCustomerByName() {

        Mockito.when(mockDao.findCustomerByName("John")).thenReturn(Optional.of(testCustomer));
        Optional<Customer> actual = customerRepositoryUnderTest.findCustomerByName("John");
        Optional<Customer> expected = Optional.of(testCustomer);
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @DisplayName("Testing find customer by email method")
    void findCustomerByEmail() {
        Mockito.when(mockDao.findCustomerByEmail("email")).thenReturn(Optional.of(testCustomer));
        Optional<Customer> actual = customerRepositoryUnderTest.findCustomerByEmail("email");
        Optional<Customer> expected = Optional.of(testCustomer);
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @DisplayName("Testing Customer list is not empty")
    void list(){
        CustomerRepository mockDao = Mockito.mock(CustomerRepository.class);
        Mockito.when(mockDao.list()).thenReturn(new ArrayList<>());
        CustomerService customerRepositoryUnderTest = new CustomerService(mockDao);
        List<Customer> actual = customerRepositoryUnderTest.list();
        List <Customer> expected = new ArrayList<>();
        assertThat(actual).isNotNull().isEqualTo(expected);


    }

}