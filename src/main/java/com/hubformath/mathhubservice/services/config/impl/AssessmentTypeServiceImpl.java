package com.hubformath.mathhubservice.services.config.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.models.config.AssessmentType;
import com.hubformath.mathhubservice.repositories.config.AssessmentTypeRepository;
import com.hubformath.mathhubservice.services.config.IAssessmentTypeService;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@Service
public class AssessmentTypeServiceImpl implements IAssessmentTypeService{
    private final AssessmentTypeRepository assessmentTypeRepository;
    private final String notFoundItemName;
    
    public AssessmentTypeServiceImpl(AssessmentTypeRepository assessmentTypeRepository) {
        super();
        this.assessmentTypeRepository = assessmentTypeRepository;
        this.notFoundItemName = "assessment type";
    }

    @Override
    public List<AssessmentType> getAllAssessmentTypes() {
        return assessmentTypeRepository.findAll();
    }

    @Override
    public AssessmentType getAssessmentTypeById(Long id) {
        Optional<AssessmentType> assessmentType = assessmentTypeRepository.findById(id);

        if(assessmentType.isPresent()){
            return assessmentType.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public AssessmentType createAssessmentType(AssessmentType assessmentTypeRequest) {
        return assessmentTypeRepository.save(assessmentTypeRequest);
    }

    @Override
    public AssessmentType updateAssessmentType(Long id, AssessmentType assessmentTypeRequest) {
        return assessmentTypeRepository.findById(id) 
                .map(assessmentType -> {
                    assessmentType.setTypeName(assessmentTypeRequest.getTypeName());
                    assessmentType.setTypeDescription(assessmentTypeRequest.getTypeDescription());
                    return assessmentTypeRepository.save(assessmentType);
                }) 
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteAssessmentType(Long id) {
        AssessmentType assessmentType = assessmentTypeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        assessmentTypeRepository.delete(assessmentType);
    } 
}
