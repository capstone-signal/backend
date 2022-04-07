package com.hidiscuss.backend.entity;

import lombok.Getter;
@Getter
public enum UserRole {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private String role;

    UserRole(String role) {
        this.role = role;
    }
}
