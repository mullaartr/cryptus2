package com.example.cryptus.service;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dao.MapDatabase;


import com.example.cryptus.model.Address;
import com.example.cryptus.model.Adres;
import com.example.cryptus.model.Customer;
import com.example.cryptus.service.AuthenticatieService;
import com.example.cryptus.service.HashService;
import com.example.cryptus.service.LoginService;
import com.example.cryptus.service.RegistrationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
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
    private CustomerDaoJdbc customerDaoJdbcunderTest;


    @Autowired
    public HashHelperTest(CustomerDaoJdbc customerDaoJdbc) {
        super();
        customerDaoJdbcunderTest = customerDaoJdbc;
    }

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        //PepperService pepperService = new PepperService();
        ww1  ="12345";
        ww2 = "foutiefWW";
        ww3 = "nepWachtwoord";
        user = "mullaart";
        user2 = "nepUser";
        hashService = new HashService();
        tokenDatabase = new MapDatabase();
        loginService = new LoginService(tokenDatabase, customerDaoJdbcunderTest);
        tokenDatabase.insertUsernameWithHash(user2, hashService.Hash(ww3));
        tokenDatabase.insertUsernameWithHash(user, hashService.Hash(ww1));
        authenticatieService = new AuthenticatieService(tokenDatabase, customerDaoJdbcunderTest);
        registrationService = new RegistrationService(customerDaoJdbcunderTest);
        mullaart = new Customer(1,"Rogier",null,"Mullaart","12345",
                "12345", Date.valueOf("1969-08-13"),"163647861",new Address(6,"Justine de Gouwerhof","2011GP","Haarlem"),"rogier.mullaart@gmail.com","0647185165","1");
    }


    @Test
    void hashTest() throws NoSuchAlgorithmException {
        String expected = "3f2b04468dffbaa00ae5651d8ff2586b2b6c7568e0f4796a61a01c883ecd9476";
        String actual = hashService.Hash(ww1);
        assertThat(actual).isNotNull().isEqualTo(expected);

    }

    @Test
    void hashTest2() throws NoSuchAlgorithmException {
        String expected = "05c8de4b3056144674d11cf13c47d24191dcd4091d81526ad0d582d4a5856a64";
        String actual = hashService.Hash(ww2);
        assertThat(actual).isNotNull().isEqualTo(expected);

    }

    @Test
    void registerTest() throws NoSuchAlgorithmException {

        registrationService.register(user, ww1, mullaart);
        String actual = tokenDatabase.findHashByUsername(user);
        String expected = "3f2b04468dffbaa00ae5651d8ff2586b2b6c7568e0f4796a61a01c883ecd9476";
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void authenticatieTest() throws NoSuchAlgorithmException {
        assertTrue(authenticatieService.authenticate(user, ww1));
    }

    @Test
    void loginAuthenticaterTest() throws NoSuchAlgorithmException {
        String token = loginService.login(user, ww1);
        assertTrue(authenticatieService.authenticate(token));
    }




}