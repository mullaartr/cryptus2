package com.example.cryptus.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ApplicationUserPermissionTest {

    @Test
    void testValueOf() {
        assertEquals("customer:read", ApplicationUserPermission.valueOf("CUSTOMER_READ").getPermission());
    }
}

