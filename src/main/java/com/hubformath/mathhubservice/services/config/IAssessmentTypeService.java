package com.hubformath.mathhubservice.services.config;

import java.util.List;

import com.hubformath.mathhubservice.models.config.AssessmentType;

public interface IAssessmentTypeService {
    public List<AssessmentType> getAllAssessmentTypes();

    public AssessmentType createAssessmentType(AssessmentType assessmentType);

    public AssessmentType getAssessmentTypeById(Long id);

    public AssessmentType updateAssessmentType(Long id, AssessmentType assessmentType);

    public void deleteAssessmentType(Long id);
}
