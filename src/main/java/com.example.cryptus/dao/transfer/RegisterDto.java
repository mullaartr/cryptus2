package com.example.cryptus.dao.transfer;

import com.example.cryptus.model.Address;

import java.util.Date;

public class RegisterDto {

    String firstName;
    String preposition;
    String lastName;
    java.sql.Date birthDate;
    String BSN;
    Address address;
    String phone;
    String email;
    String password;

    public RegisterDto(String firstName, String preposition, String lastName, java.sql.Date birthDate, String BSN,
                       Address address, String phone, String email, String password) {
        this.firstName = firstName;
        this.preposition = preposition;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.BSN = BSN;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.password = password;
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

    public void setBirthDate(java.sql.Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBSN() {
        return BSN;
    }

    public void setBSN(String BSN) {
        this.BSN = BSN;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}