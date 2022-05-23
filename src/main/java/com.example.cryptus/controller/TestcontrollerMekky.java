package com.example.cryptus.controller;
<<<<<<< HEAD
import com.example.cryptus.dao.CustomerDao;
import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dao.PortefeuilleDAOJdbc;
import com.example.cryptus.model.*;
import com.example.cryptus.repository.CustomerRepository;
import org.springframework.jdbc.core.JdbcTemplate;
=======
import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.User;
>>>>>>> develop

import java.sql.Date;


public class TestcontrollerMekky {
    public static void main(String[] args) {


<<<<<<< HEAD
       /* User user  = new Customer(3,"John","gg","mekky", Date.valueOf("2015-03-31"), "", "",0,"","","",0,"","","") {
        };
      */
        User user1  = new Customer(3,"John","gg","mekky","'","",Date.valueOf("2015-03-31"),"",new Address(0,"","",""),"","","") {
=======
        User user1  = new Customer(3,"John","gg","mekky","'","",Date.valueOf("2015-03-31"),"",new Address(10,"Amsterdam lane","1000","Amsterdam"),"","","") {
>>>>>>> develop
        };
        // Date.valueOf("2015-03-31"), "", "",0, new Adres(0,"","",""),"",0,""
       System.out.println(user1);




    }
}
