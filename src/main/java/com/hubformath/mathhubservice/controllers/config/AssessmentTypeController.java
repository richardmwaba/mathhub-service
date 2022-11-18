package com.hubformath.mathhubservice.controllers.config;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hubformath.mathhubservice.dtos.config.AssessmentTypeDto;
import com.hubformath.mathhubservice.models.config.AssessmentType;
import com.hubformath.mathhubservice.services.config.IAssessmentTypeService;

@RestController
@RequestMapping(path="/api/v1/sis")
public class AssessmentTypeController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IAssessmentTypeService assessmentTypeService;

    public AssessmentTypeController() {
        super();
    }

    @GetMapping("/assessmentTypes")
    public List<AssessmentTypeDto> all() {
        return assessmentTypeService.getAllAssessmentTypes().stream()
                .map(assessmentType -> modelMapper.map(assessmentType, AssessmentTypeDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/assessmentTypes")
    public ResponseEntity<AssessmentTypeDto> newAssessmentType(@RequestBody AssessmentTypeDto assessmentTypeDto) {
        AssessmentType assessmentTypeRequest = modelMapper.map(assessmentTypeDto, AssessmentType.class);
        AssessmentType newAssessmentType = assessmentTypeService.createAssessmentType(assessmentTypeRequest);

        AssessmentTypeDto assessmentTypeResponse = modelMapper.map(newAssessmentType, AssessmentTypeDto.class);

        return new ResponseEntity<>(assessmentTypeResponse, HttpStatus.CREATED);
    }

    @GetMapping("/assessmentTypes/{id}")
    public ResponseEntity<AssessmentTypeDto> one(@PathVariable Long id) {
        AssessmentType assessmentType = assessmentTypeService.getAssessmentTypeById(id);

        AssessmentTypeDto assessmentTypeResponse = modelMapper.map(assessmentType, AssessmentTypeDto.class);
                
        return ResponseEntity.ok().body(assessmentTypeResponse);
    }

    @PutMapping("/assessmentTypes/{id}")
    public ResponseEntity<AssessmentTypeDto> replaceAssessmentType(@RequestBody AssessmentTypeDto assessmentTypeDto,
            @PathVariable Long id) {
        AssessmentType assessmentTypeRequest = modelMapper.map(assessmentTypeDto, AssessmentType.class);
        AssessmentType updatedAssessmentType = assessmentTypeService.updateAssessmentType(id, assessmentTypeRequest);

        AssessmentTypeDto assessmentTypeResponse = modelMapper.map(updatedAssessmentType, AssessmentTypeDto.class);

        return ResponseEntity.ok().body(assessmentTypeResponse);
        
    }

    @DeleteMapping("/assessmentTypes/{id}")
    public ResponseEntity<?> deleteAssessmentType(@PathVariable Long id) {
        assessmentTypeService.deleteAssessmentType(id);

        return ResponseEntity.noContent().build();
    }
}
