package com.hubformath.mathhubservice.dto.systemconfig;

import java.util.UUID;

public class GradeDto {

    private UUID gradeId;

    private  String gradeName;

    private String gradeDescription;

    public UUID getGradeId() {
        return gradeId;
    }

    public void setGradeId(UUID gradeId) {
        this.gradeId = gradeId;
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
