package com.example.cryptus.service;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dao.MapDatabase;
import com.example.cryptus.model.Customer;
import com.example.cryptus.repository.CustomerRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.security.NoSuchAlgorithmException;

public class AuthenticatieService {

    private CustomerRepository customerRepository;

    private  CustomerDaoJdbc customerDaoJdbc;
    private CustomerService customerService;
    private HashService hashService;
    private MapDatabase tokendatabase;


    public AuthenticatieService( CustomerDaoJdbc customerDaoJdbc,  MapDatabase tokendatabase) {
        this.customerDaoJdbc = customerDaoJdbc;
        this.customerRepository = new CustomerRepository(customerDaoJdbc);
        this.customerService = new CustomerService(customerRepository);
        this.tokendatabase = tokendatabase;
    }

    public boolean authenticate(String username, String password) throws NoSuchAlgorithmException {
        hashService = new HashService(customerDaoJdbc);
        if(customerService.findCustomerByUsernamePassword(username).isPresent()){
            Customer customer = customerService.findCustomerByUsernamePassword(username).orElse(null);
            if(hashService.Hash(username, password).equals(customer.getPassword())){
                return true;
            }else {
                return false;
            }
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
