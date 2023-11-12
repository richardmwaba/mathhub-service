package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.AssessmentType;
import com.hubformath.mathhubservice.repository.systemconfig.AssessmentTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AssessmentTypeService {

    private final AssessmentTypeRepository assessmentTypeRepository;

    public AssessmentTypeService(AssessmentTypeRepository assessmentTypeRepository) {
        this.assessmentTypeRepository = assessmentTypeRepository;
    }

    public List<AssessmentType> getAllAssessmentTypes() {
        return assessmentTypeRepository.findAll();
    }

    public AssessmentType getAssessmentTypeById(UUID assessmentTypeId) {
        return assessmentTypeRepository.findById(assessmentTypeId).orElseThrow();
    }

    public AssessmentType createAssessmentType(AssessmentType assessmentTypeRequest) {
        return assessmentTypeRepository.save(assessmentTypeRequest);
    }

    public AssessmentType updateAssessmentType(UUID assessmentTypeId, AssessmentType assessmentTypeRequest) {
        return assessmentTypeRepository.findById(assessmentTypeId)
                                       .map(assessmentType -> {
                                           Optional.ofNullable(assessmentTypeRequest.getTypeName())
                                                   .ifPresent(assessmentType::setTypeName);
                                           Optional.ofNullable(assessmentTypeRequest.getTypeDescription())
                                                   .ifPresent(assessmentType::setTypeDescription);
                                           return assessmentTypeRepository.save(assessmentType);
                                       })
                                       .orElseThrow();
    }

    public void deleteAssessmentType(UUID assessmentTypeId) {
        AssessmentType assessmentType = assessmentTypeRepository.findById(assessmentTypeId)
                                                                .orElseThrow();

        assessmentTypeRepository.delete(assessmentType);
    }
}
