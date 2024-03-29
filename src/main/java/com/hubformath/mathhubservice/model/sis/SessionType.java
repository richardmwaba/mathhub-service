package com.hubformath.mathhubservice.model.sis;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SessionType {

    SHORT_TERM("Short-term"),
    LONG_TERM("Long-term"),
    TEMPORAL("Temporal");

    private final String description;

    SessionType(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    public static SessionType fromDescription(String name) {
        for (SessionType sessionType : SessionType.values()) {
            if (sessionType.description.equals(name)) {
                return sessionType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return description;
    }
}