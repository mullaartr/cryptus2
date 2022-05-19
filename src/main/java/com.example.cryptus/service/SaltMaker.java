package com.example.cryptus.service;

import java.security.SecureRandom;

public class SaltMaker {

    public SaltMaker() {
    }

    public String generateSalt(int saltLength ){
        SecureRandom srng = new SecureRandom();
        byte[] arr = new byte[saltLength];
        srng.nextBytes(arr);
        return arr.toString();
    }
}
