package com.example.cryptus.dao;

import com.example.cryptus.model.Customer;

import java.util.List;
import java.util.Optional;


public interface CustomerDao {

    Optional<Customer> findCustomerById(int id);
    Optional<Customer> findCustomerByIban(String iban);

    int storeCustomer(Customer customer);
    List<Customer> list();
    void update(Customer customer);
    void delete(int id);
    Optional<Customer> findCustomerByName(String name);
    Optional<Customer> findCustomerByUsernamePassword(String username);

    Optional<Customer>findCustomerByPortefeuilleId(int portefeuilleId);

    Optional<Customer> findCustomerByEmail(String email);

    //Daan: I added this method to check if an email is already in use
    List<Customer> customerByEmail(String email);

}
