package com.example.cryptus.service;

import com.example.cryptus.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RegisterCustomerService {
    private ArrayList<Customer> listOfCustomers;
    private CustomerDTO customerDTO;

    public RegisterCustomerService(ArrayList<Customer> listOfCustomers, CustomerDTO customerDTO) {
        this.listOfCustomers = listOfCustomers;
        this.customerDTO = customerDTO;
    }

    public RegisterCustomerService() {
    }

    public ArrayList<Customer> getListOfCustomers() {
        return listOfCustomers;
    }

    public void setListOfCustomers(ArrayList<Customer> listOfCustomers) {
        this.listOfCustomers = listOfCustomers;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }
}
