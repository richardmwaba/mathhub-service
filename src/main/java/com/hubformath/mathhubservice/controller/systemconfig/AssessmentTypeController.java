package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.systemconfig.AssessmentTypeDto;
import com.hubformath.mathhubservice.model.systemconfig.AssessmentType;
import com.hubformath.mathhubservice.service.systemconfig.AssessmentTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/v1/systemconfig/sis")
public class AssessmentTypeController {

    private final ModelMapper modelMapper;

    private final AssessmentTypeService assessmentTypeService;

    @Autowired
    public AssessmentTypeController(final ModelMapperConfig modelMapperConfig, final AssessmentTypeService assessmentTypeService
    ) {
        this.modelMapper = modelMapperConfig.createModelMapper();
        this.assessmentTypeService = assessmentTypeService;
    }

    @GetMapping("/assessmentTypes")
    public ResponseEntity<CollectionModel<EntityModel<AssessmentTypeDto>>> getAllAssessmentTypes() {
        List<AssessmentTypeDto> assessmentTypes = assessmentTypeService.getAllAssessmentTypes().stream()
                .map(assessmentType -> modelMapper.map(assessmentType, AssessmentTypeDto.class))
                .toList();
        CollectionModel<EntityModel<AssessmentTypeDto>> assessmentTypeCollectionModel = toCollectionModel(assessmentTypes);

        return ResponseEntity.ok().body(assessmentTypeCollectionModel);
    }

    @PostMapping("/assessmentTypes")
    public ResponseEntity<EntityModel<AssessmentTypeDto>> newAssessmentType(@RequestBody final AssessmentTypeDto assessmentTypeDto) {
        AssessmentType assessmentTypeRequest = modelMapper.map(assessmentTypeDto, AssessmentType.class);
        AssessmentType newAssessmentType = assessmentTypeService.createAssessmentType(assessmentTypeRequest);
        EntityModel<AssessmentTypeDto> assessmentTypeEntityModel = toModel(modelMapper.map(newAssessmentType, AssessmentTypeDto.class));

        return ResponseEntity.created(assessmentTypeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(assessmentTypeEntityModel);
    }

    @GetMapping("/assessmentTypes/{id}")
    public ResponseEntity<EntityModel<AssessmentTypeDto>> getAssessmentTypeById(@PathVariable final UUID assessmentTypeId) {
        try {
            AssessmentType assessmentType = assessmentTypeService.getAssessmentTypeById(assessmentTypeId);
            EntityModel<AssessmentTypeDto> assessmentTypeEntityModel = toModel(modelMapper.map(assessmentType, AssessmentTypeDto.class));
            return ResponseEntity.ok().body(assessmentTypeEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/assessmentTypes/{id}")
    public ResponseEntity<EntityModel<AssessmentTypeDto>> replaceAssessmentType(@RequestBody final AssessmentTypeDto assessmentTypeDto,
                                                                                @PathVariable final UUID assessmentTypeId) {
        try {
            AssessmentType assessmentTypeRequest = modelMapper.map(assessmentTypeDto, AssessmentType.class);
            AssessmentType updatedAssessmentType = assessmentTypeService.updateAssessmentType(assessmentTypeId, assessmentTypeRequest);
            EntityModel<AssessmentTypeDto> assessmentTypeEntityModel = toModel(modelMapper.map(updatedAssessmentType, AssessmentTypeDto.class));
            return ResponseEntity.ok().body(assessmentTypeEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/assessmentTypes/{id}")
    public ResponseEntity<String> deleteAssessmentType(@PathVariable final UUID assessmentTypeId) {
        try {
            assessmentTypeService.deleteAssessmentType(assessmentTypeId);
            return ResponseEntity.ok().body("Assessment type deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<AssessmentTypeDto> toModel(final AssessmentTypeDto assessmentType) {
        return EntityModel.of(assessmentType,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentTypeController.class).getAssessmentTypeById(assessmentType.getAssessmentTypeId()))
                        .withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentTypeController.class).getAllAssessmentTypes()).withRel("assessmentTypes"));
    }

    private CollectionModel<EntityModel<AssessmentTypeDto>> toCollectionModel(
            final Iterable<? extends AssessmentTypeDto> assessmentTypes) {
        List<EntityModel<AssessmentTypeDto>> assessmentTypeList = StreamSupport
                .stream(assessmentTypes.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(assessmentTypeList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentTypeController.class)
                        .getAllAssessmentTypes())
                .withSelfRel());
    }
}
