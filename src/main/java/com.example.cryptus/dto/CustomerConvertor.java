package com.example.cryptus.dto;


import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;
import org.springframework.stereotype.Component;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class CustomerConvertor {



    public  CustomerDTO entityToDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        Address address = customer.getAddress();

        customerDTO.setUserId(customer.getUserId());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setPreposition(customer.getPreposition());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setBirthDate((Date) customer.getBirthDate());
        customerDTO.setBSN(customer.getBSN());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setAddress(customer.getAddress());

        return customerDTO;
    }


    public List<CustomerDTO> entityToDTO( List <Customer> customer){


        return customer.stream().map(C -> entityToDTO(C)).collect(Collectors.toList());
    }

    public  Customer dtoToEntity(CustomerDTO customerDTO){
        Customer customer = new Customer();
        Address address = customer.getAddress();

        customer.setUserId(customerDTO.getUserId());
        customer.setFirstName(customerDTO.getFirstName());
        customer.setPreposition(customerDTO.getPreposition());
        customer.setLastName(customerDTO.getLastName());
        customer.setBirthDate(customerDTO.getBirthDate());
        customer.setBSN(customerDTO.getBSN());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());

        return customer;
    }

    public List <Customer> dtoToEntity(List <CustomerDTO> customerDTO){

        return customerDTO.stream().map(c -> dtoToEntity(c)).collect(Collectors.toList());
    }




}
