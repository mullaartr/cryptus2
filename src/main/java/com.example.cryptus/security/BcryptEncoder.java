package com.example.cryptus.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mindrot.jbcrypt.BCrypt.gensalt;

public class BcryptEncoder implements PasswordEncoder {

    private Pattern BCRYPT_PATTERN;
    private final Log logger = LogFactory.getLog(this.getClass());
    private final int strength = 10;
    final static String PEPPER = "iliaWavWavaZisSublisZarRvi";

    public BcryptEncoder(Pattern BCRYPT_PATTERN) {
        this.BCRYPT_PATTERN = BCRYPT_PATTERN;
    }

    public BcryptEncoder() {
    }

    public String encode(CharSequence rawPassword){
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        } else {
            return BCrypt.hashpw(rawPassword.toString(), gensalt(16) + PEPPER);
        }
    };

    public boolean matches(CharSequence rawPassword, String encodedPassword){
        if (BCrypt.checkpw(rawPassword.toString(), encodedPassword)){
            return true;
        } else return false;
    };

    public boolean upgradeEncoding(String encodedPassword) {
        if (encodedPassword != null && encodedPassword.length() != 0) {
            Matcher matcher = this.BCRYPT_PATTERN.matcher(encodedPassword);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Encoded password does not look like BCrypt: " + encodedPassword);
            } else {
                int strength = Integer.parseInt(matcher.group(2));
                return strength < this.strength;
            }
        } else {
            this.logger.warn("Empty encoded password");
            return false;
        }
    }

    public Pattern getBCRYPT_PATTERN() {
        return BCRYPT_PATTERN;
    }

    public Log getLogger() {
        return logger;
    }

    public int getStrength() {
        return strength;
    }

    public void setBCRYPT_PATTERN(Pattern BCRYPT_PATTERN) {
        this.BCRYPT_PATTERN = BCRYPT_PATTERN;
    }
}
