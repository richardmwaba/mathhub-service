package com.hubformath.mathhubservice.controllers.config;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hubformath.mathhubservice.assemblers.config.AssessmentTypeModelAssembler;
import com.hubformath.mathhubservice.dtos.config.AssessmentTypeDto;
import com.hubformath.mathhubservice.models.config.AssessmentType;
import com.hubformath.mathhubservice.services.config.IAssessmentTypeService;

@RestController
@RequestMapping(path = "/api/v1/sis")
public class AssessmentTypeController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IAssessmentTypeService assessmentTypeService;

    @Autowired
    private AssessmentTypeModelAssembler assessmentTypeModelAssembler;

    public AssessmentTypeController() {
        super();
    }

    @GetMapping("/assessmentTypes")
    public ResponseEntity<CollectionModel<EntityModel<AssessmentTypeDto>>> getAllAssessmentTypes() {
        List<AssessmentTypeDto> assessmentTypes = assessmentTypeService.getAllAssessmentTypes().stream()
                .map(assessmentType -> modelMapper.map(assessmentType, AssessmentTypeDto.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<AssessmentTypeDto>> assessmentTypeCollectionModel = assessmentTypeModelAssembler
                .toCollectionModel(assessmentTypes);

        return ResponseEntity.ok().body(assessmentTypeCollectionModel);
    }

    @PostMapping("/assessmentTypes")
    public ResponseEntity<EntityModel<AssessmentTypeDto>> newAssessmentType(
            @RequestBody AssessmentTypeDto assessmentTypeDto) {
        AssessmentType assessmentTypeRequest = modelMapper.map(assessmentTypeDto, AssessmentType.class);
        AssessmentType newAssessmentType = assessmentTypeService.createAssessmentType(assessmentTypeRequest);

        EntityModel<AssessmentTypeDto> assessmentTypeEntityModel = assessmentTypeModelAssembler
                .toModel(modelMapper.map(newAssessmentType, AssessmentTypeDto.class));

        return ResponseEntity.created(assessmentTypeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(assessmentTypeEntityModel);
    }

    @GetMapping("/assessmentTypes/{id}")
    public ResponseEntity<EntityModel<AssessmentTypeDto>> getAssessmentTypeById(@PathVariable Long id) {
        AssessmentType assessmentType = assessmentTypeService.getAssessmentTypeById(id);

        EntityModel<AssessmentTypeDto> assessmentTypeEntityModel = assessmentTypeModelAssembler
                .toModel(modelMapper.map(assessmentType, AssessmentTypeDto.class));

        return ResponseEntity.ok().body(assessmentTypeEntityModel);
    }

    @PutMapping("/assessmentTypes/{id}")
    public ResponseEntity<EntityModel<AssessmentTypeDto>> replaceAssessmentType(
            @RequestBody AssessmentTypeDto assessmentTypeDto,
            @PathVariable Long id) {
        AssessmentType assessmentTypeRequest = modelMapper.map(assessmentTypeDto, AssessmentType.class);
        AssessmentType updatedAssessmentType = assessmentTypeService.updateAssessmentType(id, assessmentTypeRequest);

        EntityModel<AssessmentTypeDto> assessmentTypeEntityModel = assessmentTypeModelAssembler
                .toModel(modelMapper.map(updatedAssessmentType, AssessmentTypeDto.class));

        return ResponseEntity.ok().body(assessmentTypeEntityModel);

    }

    @DeleteMapping("/assessmentTypes/{id}")
    public ResponseEntity<String> deleteAssessmentType(@PathVariable Long id) {
        assessmentTypeService.deleteAssessmentType(id);

        return ResponseEntity.ok().body("Assessment type deleted successfully");
    }
}
