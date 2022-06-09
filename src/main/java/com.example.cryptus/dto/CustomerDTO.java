package com.example.cryptus.dto;

import com.example.cryptus.model.Address;

import java.sql.Date;

public class CustomerDTO {


    private int userId;
    private String firstName;
    private String preposition;
    private String lastName;
    private Date birthDate;
    private String BSN;
    private String email;
    private String phone;
    private Address address;





    public CustomerDTO(int userId, String firstName, String preposition, String lastName
            , Date birthDate, String BSN, String email, String phone, Address address) {
        this.userId = userId;
        this.firstName = firstName;
        this.preposition = preposition;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.BSN = BSN;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public CustomerDTO() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPreposition() {
        return preposition;
    }

    public void setPreposition(String preposition) {
        this.preposition = preposition;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBSN() {
        return BSN;
    }

    public void setBSN(String BSN) {
        this.BSN = BSN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
