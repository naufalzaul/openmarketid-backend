package com.naufalzaul.openmarketid.constant;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private final String role;

    UserRole(String role) {this.role = role;}

    public static UserRole findByRole(String roles) {
        for (UserRole role : values()) {
            if (role.role.equalsIgnoreCase(roles)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + roles);
    }
}
