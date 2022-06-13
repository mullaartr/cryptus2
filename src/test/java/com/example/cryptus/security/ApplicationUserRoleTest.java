package com.example.cryptus.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ApplicationUserRoleTest {
    
    @Test
    void testGetGrantedAuthorities() {
        assertEquals(1, ApplicationUserRole.CUSTOMER.getGrantedAuthorities().size());
        assertEquals(7, ApplicationUserRole.ADMIN.getGrantedAuthorities().size());
    }

    @Test
    void testValueOf() {
        ApplicationUserRole actualValueOfResult = ApplicationUserRole.valueOf("CUSTOMER");
        assertEquals(1, actualValueOfResult.getGrantedAuthorities().size());
        assertTrue(actualValueOfResult.getPermissions().isEmpty());
    }
}

