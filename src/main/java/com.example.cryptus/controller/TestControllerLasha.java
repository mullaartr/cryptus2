package com.example.cryptus.controller;

import org.mindrot.jbcrypt.BCrypt;

import static org.mindrot.jbcrypt.BCrypt.gensalt;

public class TestControllerLasha {
    public static void main(String[] args) {
        String password = "abracadabra";
        String candidate = "abracadabra";
        String pepper = "iliaWavWavaZisSublisZarRvi";

        String hashed = BCrypt.hashpw(password, gensalt(16) + pepper);
        String hashed2 = BCrypt.hashpw(candidate, gensalt(16) + pepper);

        System.out.println(hashed);
        System.out.println(hashed2);

//        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));

        if (BCrypt.checkpw(candidate, hashed))
            System.out.println("It matches");
        else
            System.out.println("It does not match");

        String salt = gensalt(3);
        System.out.println(salt);
        System.out.println(salt.length());
    }


}
