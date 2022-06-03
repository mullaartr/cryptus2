package com.example.cryptus.security;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    private final String PEPPER = "iliaWavWavaZisSublisZarRvi";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

//    @Bean
//    public String hashSaltPepper(String mpWachtwoord) {
//        return BCrypt.hashpw(mpWachtwoord, BCrypt.gensalt(12) + PEPPER);
//    }
}