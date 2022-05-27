package com.example.cryptus.security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.example.cryptus.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    CUSTOMER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(CUSTOMER_READ, CUSTOMER_WRITE, TRANSACTION_READ, TRANSACTION_WRITE, PORTEFEUILLE_READ,
            PORTEFEUILLE_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
}
