package com.hubformath.mathhubservice.model.sis;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LessonPeriod {

    DAYS("Days"),
    WEEKS("Weeks"),
    MONTHS("Months");

    private final String description;

    LessonPeriod(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    public static LessonPeriod fromDescription(String name) {
        for (LessonPeriod lessonPeriod : LessonPeriod.values()) {
            if (lessonPeriod.description.equals(name)) {
                return lessonPeriod;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return description;
    }
}