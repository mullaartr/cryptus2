package com.example.cryptus.dao;

import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;
import com.example.cryptus.model.User;

import java.util.List;
import java.util.Optional;


public interface CustomerDao {

    Optional<Customer> findCustomerById(int id);
    void storeCustomer(Customer customer);
    List<Customer> list();
    void update(Customer customer);
    void delete(int id);
    Optional<Customer> findCustomerByName(String name);

    Optional<Portefeuille>findCustomerByPortefeuilleId(int portefeuilleId);

}
