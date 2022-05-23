package com.example.cryptus.controller;
import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.User;

import java.sql.Date;


public class TestcontrollerMekky {
    public static void main(String[] args) {


        User user1  = new Customer(3,"John","gg","mekky","'","",Date.valueOf("2015-03-31"),"",new Address(10,"Amsterdam lane","1000","Amsterdam"),"","","") {
        };
        // Date.valueOf("2015-03-31"), "", "",0, new Adres(0,"","",""),"",0,""
       System.out.println(user1);




    }
}
