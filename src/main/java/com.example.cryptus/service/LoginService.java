package com.example.cryptus.service;

import com.example.cryptus.dao.CustomerDao;
import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dao.MapDatabase;
import com.example.cryptus.repository.CustomerRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class LoginService {

    private MapDatabase tokenDatabase;
    private AuthenticatieService authenticatieService;
    private CustomerDaoJdbc customerDaoJdbc;

    private CustomerService customerService;
    private CustomerRepository customerRepository;


    public LoginService(MapDatabase mapDatabase, CustomerDaoJdbc customerDaoJdbc) {
        this.tokenDatabase = mapDatabase;
        this.customerDaoJdbc = customerDaoJdbc;
        this.customerRepository = new CustomerRepository(customerDaoJdbc);
        this.customerService = new CustomerService(customerRepository);
    }

    public String login(String username, String password) throws NoSuchAlgorithmException {
        authenticatieService = new AuthenticatieService(customerDaoJdbc, tokenDatabase);
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
