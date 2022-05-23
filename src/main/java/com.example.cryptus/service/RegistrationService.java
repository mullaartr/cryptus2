package com.example.cryptus.service;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dao.MapDatabase;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.User;
import com.example.cryptus.repository.CustomerRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RegistrationService {

    private CustomerRepository customerRepository;
    private CustomerDaoJdbc customerDaoJdbc;


    public RegistrationService(CustomerDaoJdbc customerDaoJdbc) {
        this.customerDaoJdbc = customerDaoJdbc;
    }

    public void register(String username, String password, Customer customer) throws NoSuchAlgorithmException {
        HashService hashService = new HashService();
        String nieuweHash = hashService.Hash(password);
        //customerDaoJdbc = new CustomerDaoJdbc(new JdbcTemplate());
        customerRepository = new CustomerRepository(customerDaoJdbc);
        customer.setUserName(username);
        customer.setPassword(nieuweHash);
        customerDaoJdbc.storeCustomer(customer);
    }


}
