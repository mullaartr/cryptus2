package com.example.cryptus.model;

import java.sql.Date;

public class Customer extends User {


    private Date birthDate;
    private String BSN;
    private String email;
    private String phone;
    private Address address;


    public Customer(int userId, String firstName, String preposition, String lastName, String password, String userName, Date birthDate, String BSN,
                    Address address, String email, String phone, String salt) {
        super(userId, firstName, preposition, lastName, userName, password, salt);
        this.birthDate = birthDate;
        this.BSN = BSN;
        this.email = email;
        this.phone = phone;
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

    public String getSalt() {
        return super.getSalt();
    }

    public void setSalt(String salt) {
        super.setSalt(salt);
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
                " birthDate=" + birthDate +
                ", BSN='" + BSN + '\'' +
                ", city='" + address.getCity() + '\'' +
                ", Street='" + address.getStreet() + '\'' +
                ", houseNumber=" + address.getHouseNumber() +
                ", email='" + email + '\'' +
                ", postcode='" + address.getPostcode() + '\'' +
                ", phone=" + phone +
                '}';
    }
}

