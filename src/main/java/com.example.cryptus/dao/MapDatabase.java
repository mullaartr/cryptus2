package com.example.cryptus.dao;

import com.example.cryptus.service.HashService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MapDatabase {
        // Heel simpele opslag van gebruikersnaam en wachtwoord
        private Map<String, String> db;
        private HashService hashService;



        public MapDatabase() throws NoSuchAlgorithmException {
            db = new ConcurrentHashMap<>();
            hashService = new HashService(new CustomerDaoJdbc(new JdbcTemplate()));


        }



        public String findHashByUsername(String username) {
            return db.get(username);
        }

        public boolean insertUsernameWithHash(String username, String hash) {
            if (!db.containsKey(username)) { // controleer of de nieuw aan te maken key al bestaat
                db.put(username, hash);
                return true;
            }
            return false;
        }

    public Map<String, String> getDb() {
        return db;
    }
}
