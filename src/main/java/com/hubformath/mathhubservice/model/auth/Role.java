package com.hubformath.mathhubservice.model.auth;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    ROLE_ADMINISTRATOR("Administrator"),
    ROLE_STUDENT("Student"),
    ROLE_TEACHER("Teacher"),
    ROLE_PARENT("Parent"),
    ROLE_GUEST("Guest"),
    ROLE_CASHIER("Cashier");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    public static Role fromDescription(String name) {
        for (Role role : Role.values()) {
            if (role.description.equals(name)) {
                return role;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return description;
    }

}
