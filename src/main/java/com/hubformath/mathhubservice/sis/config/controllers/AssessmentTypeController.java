package com.hubformath.mathhubservice.sis.config.controllers;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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

import com.hubformath.mathhubservice.sis.config.assemblers.AssessmentTypeModelAssembler;
import com.hubformath.mathhubservice.sis.config.models.AssessmentType;
import com.hubformath.mathhubservice.sis.config.repositories.AssessmentTypeRepository;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@RestController
@RequestMapping(path="/v1/api/sis")
public class AssessmentTypeController {
    private final AssessmentTypeRepository repository;
    private final AssessmentTypeModelAssembler assembler;

    public AssessmentTypeController(AssessmentTypeRepository repository, AssessmentTypeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/assessmentTypes")
    public CollectionModel<EntityModel<AssessmentType>> all() {
        List<EntityModel<AssessmentType>> assessmentTypes = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(assessmentTypes, linkTo(methodOn(AssessmentTypeController.class).all()).withSelfRel());
    }

    @PostMapping("/assessmentTypes")
    public ResponseEntity<EntityModel<AssessmentType>> newAssessmentType(@RequestBody AssessmentType newAssessmentType) {
        EntityModel<AssessmentType> entityModel = assembler.toModel(repository.save(newAssessmentType));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/assessmentTypes/{id}")
    public EntityModel<AssessmentType> one(@PathVariable Long id) {
        AssessmentType assessmentType = repository.findById(id) //
                .orElseThrow(() -> new ItemNotFoundException(id, "assessmentType"));

        return assembler.toModel(assessmentType);
    }

    @PutMapping("/assessmentTypes/{id}")
    public ResponseEntity<EntityModel<AssessmentType>> replaceAssessmentType(@RequestBody AssessmentType newAssessmentType,
            @PathVariable Long id) {
        AssessmentType updatedAssessmentType = repository.findById(id) //
                .map(assessmentType -> {
                    assessmentType.setTypeName(newAssessmentType.getTypeName());
                    assessmentType.setTypeDescription(newAssessmentType.getTypeDescription());
                    return repository.save(assessmentType);
                }) //
                .orElseGet(() -> {
                    newAssessmentType.setId(id);
                    return repository.save(newAssessmentType);
                });

        EntityModel<AssessmentType> entityModel = assembler.toModel(updatedAssessmentType);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/assessmentTypes/{id}")
    public ResponseEntity<?> deleteAssessmentType(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
