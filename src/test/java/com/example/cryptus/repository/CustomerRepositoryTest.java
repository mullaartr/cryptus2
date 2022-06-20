package com.example.cryptus.repository;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dao.PortefeuilleDAO;
import com.example.cryptus.dao.PortefeuilleDAOJdbc;
import com.example.cryptus.model.*;
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
    PortefeuilleDAOJdbc mockPortefeuilleDAO;
    PortefeuilleRepository portefeuilleRepositoryUnderTest;



    @BeforeEach
    public void initTest(){
        mockDao = Mockito.mock(CustomerDaoJdbc.class);
        mockPortefeuilleDAO= Mockito.mock(PortefeuilleDAOJdbc.class);
        customerRepositoryUnderTest = new CustomerRepository(mockDao);
        portefeuilleRepositoryUnderTest = new PortefeuilleRepository(mockPortefeuilleDAO,mockDao);


        testCustomer = new Customer(3,"John","gg","mekky","password","username"
                , Date.valueOf("2015-03-31"),"",new Address(0,"","",""),"");

    }



    @Test
    @DisplayName("Testing find customer by ID method")
    void getCustomerById() {

        Mockito.when(mockDao.getCustomerById(3)).thenReturn(Optional.of(testCustomer));
        customerRepositoryUnderTest = new CustomerRepository(mockDao);
        Optional<Customer> actual = customerRepositoryUnderTest.getCustomerById(3);

        Optional<Customer> expected = Optional.ofNullable(testCustomer);
        assertThat(actual).isNotNull().isEqualTo(expected);




    }

    @Test
    @DisplayName("Testing store customer method")
    void storeCustomer() {
        customerRepositoryUnderTest.storeCustomer(testCustomer);
        Mockito.when(mockDao.getCustomerById(3)).thenReturn(Optional.of(testCustomer));
        Optional<Customer> actual = customerRepositoryUnderTest.getCustomerById(3);
        Optional<Customer> expected = Optional.of(testCustomer);
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @DisplayName("Testing update customer method")
    void update() {
        Customer actual = new Customer(11,"John","gg","mekky","'","",
                Date.valueOf("2015-03-31"),"",new Address(0,"","",""),"122");
        actual.setLastName("James");
        customerRepositoryUnderTest.update(actual);
        Customer expected = new Customer(11,"John","gg","James","'","",
                Date.valueOf("2015-03-31"),"",new Address(0,"","",""),"122");

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
//        testCustomer = new Customer(3,"John","gg","mekky","password","username",new ArrayList<>(),new BankAccount("111111",500,3),new Portefeuille()
//                , Date.valueOf("2015-03-31"),"",new Address(0,"","",""),"email","");
        testCustomer = new Customer(3,"John","gg","mekky","password","username"
                , Date.valueOf("2015-03-31"),"",new Address(0,"","",""),"");

        Portefeuille portefeuille =  new Portefeuille( 5,testCustomer,new ArrayList<>());
        portefeuille.setOwner(testCustomer);


        Mockito.when(mockDao.findCustomerByName("John")).thenReturn(Optional.of(testCustomer));
        Mockito.when(mockPortefeuilleDAO.findPortefeuilleById(3)).thenReturn(Optional.of(portefeuille));
        Optional<Customer> actual = customerRepositoryUnderTest.findCustomerByName("John");
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