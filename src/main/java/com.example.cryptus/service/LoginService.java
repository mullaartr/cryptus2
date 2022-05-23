package com.example.cryptus.service;

import com.example.cryptus.dao.CustomerDao;
import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dao.MapDatabase;
import com.example.cryptus.repository.CustomerRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class LoginService {

    private MapDatabase mapDatabase;
    private MapDatabase tokenDatabase;
    private AuthenticatieService authenticatieService;
    private CustomerDaoJdbc customerDaoJdbc;


    public LoginService(MapDatabase mapDatabase, CustomerDaoJdbc customerDaoJdbc) {
        this.tokenDatabase = mapDatabase;
        this.customerDaoJdbc = customerDaoJdbc;
    }

    public String login(String username, String password) throws NoSuchAlgorithmException {
        //CustomerDaoJdbc customerDaoJdbc = new CustomerDaoJdbc(new JdbcTemplate());
        //CustomerRepository customerRepository = new CustomerRepository(customerDaoJdbc);
        authenticatieService = new AuthenticatieService(tokenDatabase, customerDaoJdbc);
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
