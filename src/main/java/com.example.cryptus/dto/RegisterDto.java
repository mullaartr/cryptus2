package com.example.cryptus.dto;

import com.example.cryptus.model.Address;
import com.example.cryptus.model.BankAccount;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

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
    String userName;
    BankAccount bankAccount;

    public RegisterDto(String firstName, String preposition, String lastName, BankAccount bankAccount, java.sql.Date birthDate, String BSN,
                       Address address, String phone, String password, String userName) {
        this.firstName = firstName;
        this.preposition = preposition;
        this.lastName = lastName;
        this.bankAccount = bankAccount;
        this.birthDate = birthDate;
        this.BSN = BSN;
        this.address = address;
        this.phone = phone;
        this.password = password;
        this.userName = userName;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
