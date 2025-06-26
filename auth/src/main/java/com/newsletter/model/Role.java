package com.newsletter.model;

public enum Role {
    USER("user"),
    ADMIN("admin");

    final String role;
    Role(String role) {
        this.role = role;
    }
}
