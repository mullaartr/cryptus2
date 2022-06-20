package com.example.cryptus.model;

import com.example.cryptus.dto.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;

public class Customer extends User {

    private Date birthDate;
    private String BSN;

    private String phone;
    private Address address;


    @Autowired
    public Customer(int userId, String firstName, String preposition, String lastName, String password,
                    String userName, Date birthDate, String BSN,
                    Address address, String phone) {
        super(userId, firstName, preposition, lastName, userName, password);
        this.birthDate = birthDate;
        this.BSN = BSN;
        this.phone = phone;
        this.address = address;
    }

    public Customer(BankAccount bankAccount) {
        super();
    }

    public Customer(int userId, String firstName, String preposition, String lastName, String userName, String password, List<Transaction> transactionList,
                    BankAccount bankAccount, Portefeuille portefeuille, Date birthDate, String BSN, String phone, Address address) {
        super(userId, firstName, preposition, lastName, userName, password, transactionList, bankAccount, portefeuille);
        this.birthDate = birthDate;
        this.BSN = BSN;
        this.phone = phone;
        this.address = address;
    }

    public Customer(int userId, Address address) {
        super(userId);
        this.address = address;
    }

    public Customer(int userId, String firstName, String preposition, String lastName, String userName, String password) {
        super(userId, firstName, preposition, lastName, userName, password);
    }

    //Daan: constructor for registerDto
    public Customer(RegisterDto registerDto) {
        super(registerDto.getFirstName(), registerDto.getPreposition(), registerDto.getLastName(),
                registerDto.getUserName(), registerDto.getPassword(), registerDto.getBankAccount());
        this.birthDate = registerDto.getBirthDate();
        this.BSN = registerDto.getBSN();
        this.address = registerDto.getAddress();
        this.phone = registerDto.getPhone();

    }

    //Daan: for registration
    public Customer(int userId) {
        super(userId);
    }

    //Daan: no-args constructor
    public Customer() {

    }

    //Daan: I made getter and setter for Address
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getUserId() {
        return super.getUserId();
    }

    public void setUserId(int userId) {
        super.setUserId(userId);
    }

    public String getFirstName() {
        return super.getFirstName();
    }

    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    public String getPreposition() {
        return super.getPreposition();
    }

    public void setPreposition(String preposition) {
        super.setPreposition(preposition);
    }

    public String getLastName() {
        return super.getLastName();
    }

    public void setLastName(String lastName) {
        super.setLastName(lastName);
    }

    public String getUserName() {
        return super.getUserName();
    }

    public void setUserName(String userName) {
        super.setUserName(userName);
    }

    public String getPassword() {
        return super.getPassword();
    }

    public void setPassword(String password) {
        super.setPassword(password);
    }

    public java.util.Date getBirthDate() {
        return birthDate;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    @Override
    public String toString() {
        return "Customer{" +
                "birthDate=" + birthDate +
                ", BSN='" + BSN + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                '}';
    }
}
