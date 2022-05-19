package com.example.cryptus.controller;

import org.mindrot.jbcrypt.BCrypt;

public class TestControllerLasha {
    public static void main(String[] args) {
        String password = "abracadabra";
        String candidate = "abracadabra";
        String pepper = "iliaWavWavaZisSublisZarRvi";

        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(16) + pepper);

        System.out.println(hashed);

//        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));

        if (BCrypt.checkpw(candidate, hashed))
            System.out.println("It matches");
        else
            System.out.println("It does not match");
    }
}
