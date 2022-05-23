package com.example.cryptus.service;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dao.MapDatabase;


import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;
import com.example.cryptus.repository.CustomerRepository;
import com.example.cryptus.service.AuthenticatieService;
import com.example.cryptus.service.HashService;
import com.example.cryptus.service.LoginService;
import com.example.cryptus.service.RegistrationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

//@SpringBootTest
//@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HashHelperTest {
    private String ww1;
    private String ww2;
    private String ww3;
    private String user2;
    private String user;
    private HashService hashService;
    private RegistrationService registrationService;
    private MapDatabase tokenDatabase;
    private AuthenticatieService authenticatieService;
    private LoginService loginService;
    private Customer mullaart;
    private CustomerDaoJdbc mockDaoJDBC;
    private CustomerService mockService;
    private Customer mekky;

    void hashHelperTest(){

    }


    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        //PepperService pepperService = new PepperService();
        ww1  ="12345";
        ww2 = "foutiefWW";
        ww3 = "nepWachtwoord";
        user = "mullaart";
        user2 = "nepUser";
        mockDaoJDBC = Mockito.mock(CustomerDaoJdbc.class);
        mockService = Mockito.mock(CustomerService.class);
        mullaart = new Customer(1,"Rogier",null,"Mullaart","3f2b04468dffbaa00ae5651d8ff2586b2b6c7568e0f4796a61a01c883ecd9476",
                "mullaart", Date.valueOf("1969-08-13"),"163647861",new Address(6,"Justine de Gouwerhof","2011GP","Haarlem"),"rogier.mullaart@gmail.com","0647185165","1");
        mekky = new Customer(3,"John","gg","mekky","foutiefWW","nepUser",Date.valueOf("2015-03-31"),"",new Address(0,"","",""),"","","");
        Mockito.when(mockDaoJDBC.findCustomerByUsernamePassword(user)).thenReturn(Optional.ofNullable(mullaart));
        Mockito.when(mockDaoJDBC.findCustomerByUsernamePassword(user2)).thenReturn(Optional.ofNullable(mekky));
        hashService = new HashService(mockDaoJDBC);
        tokenDatabase = new MapDatabase();
        loginService = new LoginService(tokenDatabase, mockDaoJDBC);
        tokenDatabase.insertUsernameWithHash(user, hashService.Hash(ww1, user));
        authenticatieService = new AuthenticatieService(mockDaoJDBC, tokenDatabase);
        registrationService = new RegistrationService(mockDaoJDBC);

    }


    @Test
    void hashTest() throws NoSuchAlgorithmException {
        String expected = "29e21080035b30aa55655e3cf31004956a5807d83bdb814262e355213225abab";
        String actual = hashService.Hash(ww1, user);
        assertThat(actual).isNotNull().isEqualTo(expected);

    }

    @Test
    void hashTest2() throws NoSuchAlgorithmException {
        String expected = "05c8de4b3056144674d11cf13c47d24191dcd4091d81526ad0d582d4a5856a64";
        String actual = hashService.Hash(ww2, user2);
        assertThat(actual).isNotNull().isEqualTo(expected);

    }

    @Test
    void registerTest() throws NoSuchAlgorithmException {
        registrationService.register(mullaart);
        String actual = tokenDatabase.findHashByUsername(user);
        String expected = "29e21080035b30aa55655e3cf31004956a5807d83bdb814262e355213225abab";
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

/*
    @Test
    void authenticatieTest() throws NoSuchAlgorithmException {
        assertTrue(authenticatieService.authenticate(user, ww1));
    }

    @Test
    void loginAuthenticaterTest() throws NoSuchAlgorithmException {
        String token = loginService.login(user, ww1);
        assertTrue(authenticatieService.authenticate(token));
    }
*/




}