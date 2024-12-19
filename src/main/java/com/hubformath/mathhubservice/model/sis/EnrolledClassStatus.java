package com.hubformath.mathhubservice.model.sis;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EnrolledClassStatus {

    ACTIVE("Active"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String description;

    EnrolledClassStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    public static EnrolledClassStatus fromDescription(String name) {
        for (EnrolledClassStatus enrolledClassStatus : EnrolledClassStatus.values()) {
            if (enrolledClassStatus.description.equals(name)) {
                return enrolledClassStatus;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return description;
    }
}
