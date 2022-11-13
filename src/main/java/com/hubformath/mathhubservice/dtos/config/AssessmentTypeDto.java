package com.hubformath.mathhubservice.dtos.config;

public class AssessmentTypeDto {
    private Long id;

    private String name;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return name;
    }

    public void setTypeName(String name) {
        this.name = name;
    }

    public String getTypeDescription() {
        return description;
    }

    public void setTypeDescription(String description) {
        this.description = description;
    }
}
