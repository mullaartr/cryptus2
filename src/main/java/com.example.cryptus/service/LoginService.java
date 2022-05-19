package com.example.cryptus.service;

import com.example.cryptus.dao.MapDatabase;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class LoginService {

    private MapDatabase mapDatabase;
    private MapDatabase tokenDatabase;
    private AuthenticatieService authenticatieService;

    public LoginService(MapDatabase mapDatabase) {
        this.mapDatabase = mapDatabase;
    }

    public String login(String username, String password, MapDatabase mapDatabase) throws NoSuchAlgorithmException {
        tokenDatabase = new MapDatabase();
        authenticatieService = new AuthenticatieService(mapDatabase);
        if(authenticatieService.authenticate(username, password)){
            tokenDatabase.insertUsernameWithHash(username, UUID.randomUUID().toString());
            return tokenDatabase.findHashByUsername(username);
        } else {
            return null;
        }
    }

    public MapDatabase getTokenDatabase() {
        return tokenDatabase;
    }
}
