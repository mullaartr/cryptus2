package com.example.cryptus.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.TreeMap;

@Repository
public class JwtFakeRepo {

    Map<String, String>jwtRepo = new TreeMap<>();

    public void addJwtToRepo(String username, String token) {
        jwtRepo.put(username, token);
    }

    public void removeJwtFromRepo(String username){
        jwtRepo.remove(username);
    }

    public boolean containsKey(String username){
        return jwtRepo.containsKey(username);
    }
}
