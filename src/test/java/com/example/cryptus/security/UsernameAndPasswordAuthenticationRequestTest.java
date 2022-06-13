package com.example.cryptus.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UsernameAndPasswordAuthenticationRequestTest {

    @Test
    void testConstructor() {
        UsernameAndPasswordAuthenticationRequest actualUsernameAndPasswordAuthenticationRequest = new UsernameAndPasswordAuthenticationRequest();
        actualUsernameAndPasswordAuthenticationRequest.setPassword("cryptusisthebest");
        actualUsernameAndPasswordAuthenticationRequest.setUsername("jesuscryptus");
        assertEquals("cryptusisthebest", actualUsernameAndPasswordAuthenticationRequest.getPassword());
        assertEquals("jesuscryptus", actualUsernameAndPasswordAuthenticationRequest.getUsername());
    }
}

