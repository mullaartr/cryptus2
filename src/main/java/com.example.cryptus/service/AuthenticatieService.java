package com.example.cryptus.service;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dao.MapDatabase;
import com.example.cryptus.model.Customer;
import com.example.cryptus.repository.CustomerRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.security.NoSuchAlgorithmException;

public class AuthenticatieService {

    private CustomerRepository customerRepository;
    private HashService hashService;
    private MapDatabase tokendatabase;

    private CustomerDaoJdbc customerDaoJdbc;




    public AuthenticatieService(MapDatabase tokendatabase, CustomerDaoJdbc customerDaoJdbc) {
        this.tokendatabase = tokendatabase;
        this.customerDaoJdbc = customerDaoJdbc;

    }

    public boolean authenticate(String username, String password) throws NoSuchAlgorithmException {
        //CustomerDaoJdbc customerDaoJdbc = new CustomerDaoJdbc(new JdbcTemplate());
        customerRepository = new CustomerRepository(customerDaoJdbc);
        hashService = new HashService();
        Customer customer = customerRepository.findCustomerByUsernamePassword(username, hashService.Hash(password)).orElse(null);
        if(customerRepository.findCustomerByUsernamePassword(username, hashService.Hash(password)).isPresent()){
            return true;
        } else {
            return false;
        }
    }

    public boolean authenticate(String token) {
        if(tokendatabase.getDb().containsValue(token)){
            return true;
        } else {
            return false;
        }
    }
}
