package com.hubformath.mathhubservice.model.systemconfig;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SubjectComplexity {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");

    private final String description;

    SubjectComplexity(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    public static SubjectComplexity fromDescription(String name) {
        for (SubjectComplexity subjectComplexity : SubjectComplexity.values()) {
            if (subjectComplexity.description.equals(name)) {
                return subjectComplexity;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return description;
    }
}
