package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.AssessmentType;
import com.hubformath.mathhubservice.repository.systemconfig.AssessmentTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssessmentTypeService {

    private final AssessmentTypeRepository assessmentTypeRepository;

    public AssessmentTypeService(AssessmentTypeRepository assessmentTypeRepository) {
        this.assessmentTypeRepository = assessmentTypeRepository;
    }

    public List<AssessmentType> getAllAssessmentTypes() {
        return assessmentTypeRepository.findAll();
    }

    public AssessmentType getAssessmentTypeById(String assessmentTypeId) {
        return assessmentTypeRepository.findById(assessmentTypeId).orElseThrow();
    }

    public AssessmentType createAssessmentType(AssessmentType assessmentTypeRequest) {
        return assessmentTypeRepository.save(assessmentTypeRequest);
    }

    public AssessmentType updateAssessmentType(String assessmentTypeId, AssessmentType assessmentTypeRequest) {
        return assessmentTypeRepository.findById(assessmentTypeId)
                                       .map(assessmentType -> {
                                           Optional.ofNullable(assessmentTypeRequest.getName())
                                                   .ifPresent(assessmentType::setName);
                                           Optional.ofNullable(assessmentTypeRequest.getDescription())
                                                   .ifPresent(assessmentType::setDescription);
                                           return assessmentTypeRepository.save(assessmentType);
                                       })
                                       .orElseThrow();
    }

    public void deleteAssessmentType(String assessmentTypeId) {
        AssessmentType assessmentType = assessmentTypeRepository.findById(assessmentTypeId)
                                                                .orElseThrow();

        assessmentTypeRepository.delete(assessmentType);
    }
}
