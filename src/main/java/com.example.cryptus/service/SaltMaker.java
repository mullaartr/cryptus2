package com.example.cryptus.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class SaltMaker {

    private final Logger logger =  LogManager.getLogger(SaltMaker.class);

    public SaltMaker() {
        logger.info("Saltmaker created");
    }

    public String generateSalt(int saltLength ){
        SecureRandom srng = new SecureRandom();
        byte[] arr = new byte[saltLength];
        srng.nextBytes(arr);
        return arr.toString();
    }
}
