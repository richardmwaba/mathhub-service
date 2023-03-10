package com.hubformath.mathhubservice.dto.systemconfig;

public class GradeDto {

    private Long id;

    private  String gradeName;

    private String gradeDescription;

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

    public String getGradeDescription() {return gradeDescription;}

    public void setGradeDescription(String gradeDescription) {this.gradeDescription = gradeDescription;}
}
