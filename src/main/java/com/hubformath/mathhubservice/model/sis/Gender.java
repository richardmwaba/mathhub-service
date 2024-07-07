package com.hubformath.mathhubservice.model.sis;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other"),
    PREFER_NOT_TO_SAY("Prefer not to say");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    @SuppressWarnings("unused")
    public static Gender fromDescription(String description) {
        for (Gender gender : Gender.values()) {
            if (gender.getDescription().equals(description)) {
                return gender;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
