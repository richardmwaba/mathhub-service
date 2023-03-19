package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.AssessmentType;
import com.hubformath.mathhubservice.repository.systemconfig.AssessmentTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentTypeService{

    private final AssessmentTypeRepository assessmentTypeRepository;

    public AssessmentTypeService(AssessmentTypeRepository assessmentTypeRepository) {
        this.assessmentTypeRepository = assessmentTypeRepository;
    }

    public List<AssessmentType> getAllAssessmentTypes() {
        return assessmentTypeRepository.findAll();
    }

    public AssessmentType getAssessmentTypeById(Long id) {
        return assessmentTypeRepository.findById(id).orElseThrow();
    }

    public AssessmentType createAssessmentType(AssessmentType assessmentTypeRequest) {
        return assessmentTypeRepository.save(assessmentTypeRequest);
    }

    public AssessmentType updateAssessmentType(Long id, AssessmentType assessmentTypeRequest) {
        return assessmentTypeRepository.findById(id) 
                .map(assessmentType -> {
                    assessmentType.setTypeName(assessmentTypeRequest.getTypeName());
                    assessmentType.setTypeDescription(assessmentTypeRequest.getTypeDescription());
                    return assessmentTypeRepository.save(assessmentType);
                }) 
                .orElseThrow();
    }

    public void deleteAssessmentType(Long id) {
        AssessmentType assessmentType = assessmentTypeRepository.findById(id)
                .orElseThrow();

        assessmentTypeRepository.delete(assessmentType);
    } 
}
