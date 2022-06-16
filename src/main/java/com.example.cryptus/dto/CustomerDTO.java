package com.example.cryptus.dto;

import com.example.cryptus.model.Address;
import com.example.cryptus.model.BankAccount;
import com.example.cryptus.model.Customer;

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
    private PortefeuilleDTO portefeuilleDTO;

    private BankAccountDTo bankAccountDTo;






    public CustomerDTO(Customer customer) {
        this.userId = customer.getUserId();
        this.firstName = customer.getFirstName();
        this.preposition = customer.getPreposition();
        this.lastName = customer.getLastName();
        this.birthDate = (Date) customer.getBirthDate();
        this.BSN = customer.getBSN();
        this.email = customer.getEmail();
        this.phone = customer.getPhone();
        this.address = customer.getAddress();
        this.portefeuilleDTO = new PortefeuilleDTO(customer.getPortefeuille());
        this.bankAccountDTo = new BankAccountDTo(customer.getBankAccount());
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

    public PortefeuilleDTO getPortefeuilleDTO() {
        return portefeuilleDTO;
    }

    public void setPortefeuilleDTO(PortefeuilleDTO portefeuilleDTO) {
        this.portefeuilleDTO = portefeuilleDTO;
    }

    public BankAccountDTo getBankAccountDTo() {
        return bankAccountDTo;
    }

    public void setBankAccountDTo(BankAccountDTo bankAccountDTo) {
        this.bankAccountDTo = bankAccountDTo;
    }
}
