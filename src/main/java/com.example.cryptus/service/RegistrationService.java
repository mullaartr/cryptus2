package com.example.cryptus.service;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dao.MapDatabase;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.User;
import com.example.cryptus.repository.CustomerRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RegistrationService {

    private CustomerRepository customerRepository;
    private CustomerDaoJdbc customerDaoJdbc;


    private final int saltLength = 8;


    public RegistrationService(CustomerDaoJdbc customerDaoJdbc) {
        this.customerDaoJdbc = customerDaoJdbc;
    }

    public void register(Customer customer) throws NoSuchAlgorithmException {
        HashService hashService = new HashService(customerDaoJdbc);
        customerRepository = new CustomerRepository(customerDaoJdbc);
        SaltMaker saltMaker = new SaltMaker();
        customer.setSalt(saltMaker.generateSalt(saltLength));
        String nieuweHash = hashService.Hash(customer.getPassword(), customer.getUserName());
        customer.setPassword(nieuweHash);
        customerDaoJdbc.storeCustomer(customer);
    }


}
