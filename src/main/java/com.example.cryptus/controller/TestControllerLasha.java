/*
package com.example.cryptus.controller;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dao.VolledigeTransactionDaoJdbc;
import com.example.cryptus.model.Customer;


import static org.mindrot.jbcrypt.BCrypt.gensalt;

public class TestControllerLasha {
    private static CustomerDaoJdbc customerDaoJdbc;
    private static TransactionDaoJdbc transactionDaoJdbc;
    private static StatusController statusController;
    private static VolledigeTransactionDaoJdbc transactionDaoJdbc;

    public static void main(String[] args) {
        String password = "abracadabra";
        String candidate = "abracadabra";
        String pepper = "iliaWavWavaZisSublisZarRvi";

//        String hashed = BCrypt.hashpw(password, gensalt(16) + pepper);
//        String hashed2 = BCrypt.hashpw(candidate, gensalt(16) + pepper);

//        System.out.println(hashed);
//        System.out.println(hashed2);

//        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));

//        if (BCrypt.checkpw(candidate, hashed))
//            System.out.println("It matches");
//        else
//            System.out.println("It does not match");

        String salt = gensalt(3);
        System.out.println(salt);
        System.out.println(salt.length());

        Customer Baruch = new Customer(0, "Baruch", null, "Spinoza",
                "baruch@spinoza.com", "baruchspinoza");

//        TEST TO EXTRACT ALL USERS AS LIST FROM DATABASE
//        CURRENTLY NOT!!!
//        List<Customer> allCustomers = customerDaoJdbc.list();

//        System.out.println(Arrays.toString(allCustomers.toArray()));

//        System.out.println(Baruch.getTransactionList());
//
//        Boolean username = jwtFakeRepo.containsKey("odette@swann.nl");
//        System.out.println(username);
    }

}
*/
