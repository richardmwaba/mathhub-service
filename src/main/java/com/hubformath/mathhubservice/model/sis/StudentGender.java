package com.hubformath.mathhubservice.model.sis;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StudentGender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other"),
    PREFER_NOT_TO_SAY("Prefer not to say");

    private final String description;

    StudentGender(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    public static StudentGender fromDescription(String description) {
        for (StudentGender studentGender : StudentGender.values()) {
            if (studentGender.getDescription().equals(description)) {
                return studentGender;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
