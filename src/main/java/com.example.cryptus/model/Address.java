package com.example.cryptus.model;

public class Address {
    private int houseNumber;
    private String Street;
    private String postcode;
    private String city;

    public Address() {
    }

    public Address(int houseNumber, String street, String postcode, String city) {
        this.houseNumber = houseNumber;
        Street = street;
        this.postcode = postcode;
        this.city = city;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "houseNumber=" + houseNumber +
                ", Street='" + Street + '\'' +
                ", postcode='" + postcode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public void setCity(String city) {
        this.city = city;
    }
}
