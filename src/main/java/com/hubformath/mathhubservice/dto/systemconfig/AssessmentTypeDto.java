package com.hubformath.mathhubservice.dto.systemconfig;

public class AssessmentTypeDto {

    private String assessmentTypeId;

    private String typeName;

    private String typeDescription;

    public String getAssessmentTypeId() {
        return assessmentTypeId;
    }

    public void setAssessmentTypeId(String assessmentTypeId) {
        this.assessmentTypeId = assessmentTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }
}
