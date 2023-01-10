package com.hubformath.mathhubservice.dtos.config;

public class GradeDto {

    private Long id;

    private  String gradeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }
}
