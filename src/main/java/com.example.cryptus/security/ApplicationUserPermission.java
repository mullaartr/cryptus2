package com.example.cryptus.security;

public enum ApplicationUserPermission {

    CUSTOMER_READ("customer:read"),
    CUSTOMER_WRITE("customer:write"),
    TRANSACTION_READ("transaction:read"),
    TRANSACTION_WRITE("transaction:write"),
    PORTEFEUILLE_READ("portefeuille:read"),
    PORTEFEUILLE_WRITE("portefeuille:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
