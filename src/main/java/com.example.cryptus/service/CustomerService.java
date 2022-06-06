package com.example.cryptus.service;

import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;
import com.example.cryptus.model.User;
import com.example.cryptus.repository.CustomerRepository;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {


    private final Logger logger =  LogManager.getLogger(CustomerService.class);
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        logger.info("New CustomerService");
    }

    public void storeCustomer(Customer customer) {
        customerRepository.storeCustomer(customer);


    }


    public void update(Customer customer) {
        customerRepository.update(customer);

    }


    public void delete(int id) {
        customerRepository.delete(id);

    }
    public Optional<Customer> findCustomerByName(String name){
        return customerRepository.findCustomerByName(name);

    }
    public Optional<Customer> findCustomerByUsernamePassword(String username){
        return customerRepository.findCustomerByUsernamePassword(username);
    }

    public Optional <Customer> findCustomerById (int id){
        return customerRepository.findCustomerById(id);
    }

    public List<Customer> list(){
        return customerRepository.list();
    }



    public Optional<Customer> findCustomerByPortefeuilleId(int portefeuilleId) {
        return customerRepository.findCustomerByPortefeuilleId(portefeuilleId);
    }


    public Optional<Customer> findCustomerByEmail(String email){
        return customerRepository.findCustomerByEmail(email);
    }

    //Daan: added this to check registration
    public List<Customer> customerByEmail(String email){
        List<Customer> customers = customerRepository.customerByEmail(email);
        return customers;
    }
}
