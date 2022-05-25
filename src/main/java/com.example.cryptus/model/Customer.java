package com.example.cryptus.model;

import com.example.cryptus.dao.transfer.RegisterDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;

public class Customer extends User {

    //Daan: I made a logger
    private final Logger logger = LoggerFactory.getLogger(Customer.class);

    private Date birthDate;
    private String BSN;
    private String email;
    private String phone;
    private Address address;

        @Autowired
        public Customer(int userId, String firstName, String preposition, String lastName, String password,
                        String userName, Date birthDate, String BSN,
                        Address address, String email, String phone) {
        super(userId, firstName, preposition, lastName, userName, password);
        this.birthDate = birthDate;
        this.BSN = BSN;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Customer() {
            super();
    }


    public Customer(int userId, String firstName, String preposition, String lastName, String userName, String password) {
        super(userId, firstName, preposition, lastName, userName, password);
    }

    //Daan: I made this no-args constructor
    public Customer(RegisterDto registerDto) {
        super();
        //logger.info("Customer created with no-arg constructor");
    }

    //Daan: I made this constructor for registration
    public Customer(String firstName, String preposition, String lastName, java.util.Date birthDate, String bsn,
                    Address address, String phone, String email, String password) {
        super();
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

    public Date getBirthDate() {
        return birthDate;
    }

    public String getStreet() {
        return address.getStreet();
    }

    public void setStreet(String street) {
        address.setStreet(street);
    }

    public int getHouseNumber() {
        return address.getHouseNumber();
    }

    public String getPostcode() {
        return address.getPostcode();
    }

    public void setPostcode(String postcode) {
        address.setPostcode(postcode);
    }

    public void setHouseNumber(int houseNumber) {
        address.setHouseNumber(houseNumber);
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCity() {
        return address.getCity();
    }

    public void setCity(String city) {
        address.setCity(city);
    }

    @Override
    public String toString() {
        return     super.toString()+
                " BirthDate= " + birthDate +'\n' +
                " BSN= " + BSN + '\n' +
                " City= " + address.getCity() + '\n' +
                " Street= " + address.getStreet() + '\n' +
                " HouseNumber=" + address.getHouseNumber() +'\n' +
                " email= " + email + '\n' +
                " Postcode= " + address.getPostcode() + '\n' +
                " Phone= " + phone ;
    }
}
