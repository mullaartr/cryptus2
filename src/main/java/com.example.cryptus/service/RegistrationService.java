package com.example.cryptus.service;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dao.MapDatabase;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.User;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RegistrationService {

    private MapDatabase mapDatabase;


    public RegistrationService(MapDatabase mapDatabase) {
        this.mapDatabase = mapDatabase;
    }

    public void register(String username, String password) throws NoSuchAlgorithmException {
        HashService hashService = new HashService();
        String nieuweHash = hashService.Hash(password);
        mapDatabase.insertUsernameWithHash(username, nieuweHash );
    }


}
