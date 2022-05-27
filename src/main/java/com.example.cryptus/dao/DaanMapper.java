/*
package com.example.cryptus.dao;

import com.example.cryptus.dao.transfer.RegisterDto;
import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;

import java.sql.Date;

public class DaanMapper {

    public RegisterDto toDto(Customer customer) {
        String firstName = customer.getFirstName();
        String preposition = customer.getPreposition();
        String lastName = customer.getLastName();
        Date birthDate = customer.getBirthDate();
        String BSN = customer.getBSN();
        Address address = customer.getAddress();
        String phone = customer.getPhone();
        String email = customer.getEmail();
        String password = customer.getPassword();

        return new RegisterDto(firstName, preposition, lastName, birthDate, BSN, address,
                phone, email, password);
    }

    public Customer toCustomer(RegisterDto registerDto) {
        return new Customer(registerDto.getFirstName(), registerDto.getPreposition(), registerDto.getLastName(),
                registerDto.getBirthDate(), registerDto.getBSN(), registerDto.getAddress(), registerDto.getPhone(),
                registerDto.getEmail(), registerDto.getPassword());
    }

}
*/
