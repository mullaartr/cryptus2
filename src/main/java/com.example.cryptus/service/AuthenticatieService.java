package com.example.cryptus.service;

import com.example.cryptus.dao.MapDatabase;

import java.security.NoSuchAlgorithmException;

public class AuthenticatieService {
    private MapDatabase mapDatabase;
    private HashService hashService;

    public AuthenticatieService(MapDatabase mapDatabase) {
        this.mapDatabase = mapDatabase;
    }

    public boolean authenticate(String username, String password) throws NoSuchAlgorithmException {

        hashService = new HashService();

        if(mapDatabase.findHashByUsername(username).equals(hashService.Hash(password))){
            return true;
        } else {
            return false;
        }
    }

    public boolean authenticate(String token, MapDatabase tokenDatabase) {
        if(tokenDatabase.getDb().containsValue(token)){
            return true;
        } else {
            return false;
        }
    }
}
