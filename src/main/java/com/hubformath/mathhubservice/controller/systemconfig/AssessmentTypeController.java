package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.controller.util.CollectionModelUtils;
import com.hubformath.mathhubservice.model.systemconfig.AssessmentType;
import com.hubformath.mathhubservice.service.systemconfig.AssessmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

@RestController
@RequestMapping(path = "/api/v1/systemconfig/sis")
@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER')")
public class AssessmentTypeController {

    private final AssessmentTypeService assessmentTypeService;

    @Autowired
    public AssessmentTypeController(AssessmentTypeService assessmentTypeService) {
        this.assessmentTypeService = assessmentTypeService;
    }

    @GetMapping("/assessmentTypes")
    public ResponseEntity<CollectionModel<?>> getAllAssessmentTypes() {
        List<AssessmentType> assessmentTypes = assessmentTypeService.getAllAssessmentTypes().stream()
                                                                    .toList();

        return ResponseEntity.ok().body(toCollectionModel(assessmentTypes));
    }

    @PostMapping("/assessmentTypes")
    public ResponseEntity<EntityModel<AssessmentType>> createAssessmentType(@RequestBody AssessmentType assessmentTypeRequest) {
        AssessmentType newAssessmentType = assessmentTypeService.createAssessmentType(assessmentTypeRequest);
        EntityModel<AssessmentType> assessmentTypeEntityModel = toModel(newAssessmentType);

        return ResponseEntity.created(assessmentTypeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(assessmentTypeEntityModel);
    }

    @GetMapping("/assessmentTypes/{assessmentTypeId}")
    public ResponseEntity<EntityModel<AssessmentType>> getAssessmentTypeById(@PathVariable String assessmentTypeId) {
        try {
            AssessmentType assessmentType = assessmentTypeService.getAssessmentTypeById(assessmentTypeId);
            EntityModel<AssessmentType> assessmentTypeEntityModel = toModel(assessmentType);

            return ResponseEntity.ok().body(assessmentTypeEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/assessmentTypes/{assessmentTypeId}")
    public ResponseEntity<EntityModel<AssessmentType>> replaceAssessmentType(@RequestBody AssessmentType assessmentTypeRequest,
                                                                             @PathVariable String assessmentTypeId) {
        try {
            AssessmentType updatedAssessmentType = assessmentTypeService.updateAssessmentType(assessmentTypeId,
                                                                                              assessmentTypeRequest);
            EntityModel<AssessmentType> assessmentTypeEntityModel = toModel(updatedAssessmentType);

            return ResponseEntity.ok().body(assessmentTypeEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/assessmentTypes/{assessmentTypeId}")
    public ResponseEntity<String> deleteAssessmentType(@PathVariable String assessmentTypeId) {
        try {
            assessmentTypeService.deleteAssessmentType(assessmentTypeId);
            return ResponseEntity.ok().body("Assessment type deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<AssessmentType> toModel(final AssessmentType assessmentType) {
        return EntityModel.of(assessmentType,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentTypeController.class)
                                                                        .getAssessmentTypeById(assessmentType.getId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentTypeController.class)
                                                                        .getAllAssessmentTypes())
                                               .withRel("assessmentTypes"));
    }

    private CollectionModel<?> toCollectionModel(List<AssessmentType> assessmentTypesList) {
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentTypeController.class)
                                                              .getAllAssessmentTypes()).withSelfRel();

        List<EntityModel<AssessmentType>> assessmentTypes = assessmentTypesList.stream()
                                                                               .map(this::toModel)
                                                                               .toList();

        return assessmentTypesList.isEmpty()
                ? CollectionModelUtils.getEmptyEmbeddedCollectionModel(AssessmentType.class, link)
                : CollectionModel.of(assessmentTypes, link);
    }
}
