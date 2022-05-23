package com.example.cryptus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerDTO {
    private String userid;
    private String firstName;
    private String preposition;
    private String lastName;
    private String birthDate;
    private String BSN;
    private String Street;
    private int houseNumber;
    private String postcode;
    private String city;
    private String email;
    private int phone;
    private String userName;
    private String password;
    private String salt;


    public CustomerDTO(String userid, String firstName, String preposition, String lastName, String birthDate, String BSN, String street, int houseNumber, String postcode, String city, String email, int phone, String userName, String password, String salt) {
        this.userid = userid;
        this.firstName = firstName;
        this.preposition = preposition;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.BSN = BSN;
        Street = street;
        this.houseNumber = houseNumber;
        this.postcode = postcode;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.userName = userName;
        this.password = password;
        this.salt = salt;
    }

    public CustomerDTO(String birthDate) {
        this.birthDate = birthDate;
    }

    public CustomerDTO() {
    }

    public String getBirthDate() {
        return birthDate;
    }
}
