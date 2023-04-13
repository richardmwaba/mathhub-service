package com.hubformath.mathhubservice.dto.systemconfig;

import java.util.UUID;

public class AssessmentTypeDto {
    private UUID assessmentTypeId;

    private String typeName;

    private String typeDescription;

    public UUID getAssessmentTypeId() {
        return assessmentTypeId;
    }

    public void setAssessmentTypeId(UUID assessmentTypeId) {
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
