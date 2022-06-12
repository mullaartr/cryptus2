package com.example.cryptus.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class JwtConfigTest {

    @Test
    void testConstructor() {
        JwtConfig actualJwtConfig = new JwtConfig();
        actualJwtConfig.setSecretKey("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
        actualJwtConfig.setTokenExpirationAfterDays(1);
        actualJwtConfig.setTokenPrefix("ABC123");
        assertEquals("Authorization", actualJwtConfig.getAuthorizationHeader());
        assertEquals("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY", actualJwtConfig.getSecretKey());
        assertEquals(1, actualJwtConfig.getTokenExpirationAfterDays().intValue());
        assertEquals("ABC123", actualJwtConfig.getTokenPrefix());
    }
}

