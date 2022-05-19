package com.example.cryptus.service;

import com.example.cryptus.dao.MapDatabase;


import com.example.cryptus.service.AuthenticatieService;
import com.example.cryptus.service.HashService;
import com.example.cryptus.service.LoginService;
import com.example.cryptus.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HashHelperTest {
    private String ww1;
    private String ww2;
    private String ww3;
    private String user2;
    private String user;
    private HashService hashService;
    private RegistrationService registrationService;
    private MapDatabase mapDatabase;
    private AuthenticatieService authenticatieService;
    private LoginService loginService;



    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        //PepperService pepperService = new PepperService();
        ww1  ="eenWachtwoord";
        ww2 = "foutiefWW";
        ww3 = "nepWachtwoord";
        user = "Sebastiaan";
        user2 = "nepUser";
        hashService = new HashService();
        mapDatabase = new MapDatabase();
        loginService = new LoginService(mapDatabase);
        mapDatabase.insertUsernameWithHash(user2, hashService.Hash(ww3));
        mapDatabase.insertUsernameWithHash(user, hashService.Hash(ww1));
        authenticatieService = new AuthenticatieService(mapDatabase);
        registrationService = new RegistrationService(mapDatabase);
    }


    @Test
    void hashTest() throws NoSuchAlgorithmException {
        String expected = "26c07fbf3f10800b38d0a8ec1a53212917f54ee1756bb5cde17b1a4ec3b4036c";
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
        registrationService.register(user, ww1);
        String actual = mapDatabase.findHashByUsername(user);
        String expected = "26c07fbf3f10800b38d0a8ec1a53212917f54ee1756bb5cde17b1a4ec3b4036c";
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void authenticatieTest() throws NoSuchAlgorithmException {
        assertTrue(authenticatieService.authenticate(user2, ww3));
    }

    @Test
    void loginAuthenticaterTest() throws NoSuchAlgorithmException {
        String token = loginService.login(user, ww1, mapDatabase);
        assertTrue(authenticatieService.authenticate(token, loginService.getTokenDatabase()));
    }




}