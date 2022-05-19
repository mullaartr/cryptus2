package com.example.cryptus.model;

public class Admin extends User{

    private int personalNumber;

    public Admin(int userid, String firstName, String preposition, String lastName,int personalNumber, String userName, String password, String salt) {
        super(userid, firstName, preposition, lastName,userName,password,salt);
        this.personalNumber =  personalNumber;
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


    public int getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(int personalNumber) {
        this.personalNumber = personalNumber;
    }

    @Override
    public String toString() {
        return super.toString()+" Admin{" +
                "personalNumber=" + personalNumber +
                '}';
    }
}
