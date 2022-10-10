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

import com.hubformath.mathhubservice.sis.config.assemblers.SessionTypeModelAssembler;
import com.hubformath.mathhubservice.sis.config.models.SessionType;
import com.hubformath.mathhubservice.sis.config.repositories.SessionTypeRepository;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@RestController
@RequestMapping(path="/v1/sis/ops")
public class SessionTypeController {
    private final SessionTypeRepository repository;
    private final SessionTypeModelAssembler assembler;

    public SessionTypeController(SessionTypeRepository repository, SessionTypeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/sessionTypes")
    public CollectionModel<EntityModel<SessionType>> all() {
        List<EntityModel<SessionType>> sessionTypes = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(sessionTypes, linkTo(methodOn(SessionTypeController.class).all()).withSelfRel());
    }

    @PostMapping("/sessionTypes")
    public ResponseEntity<EntityModel<SessionType>> newSessionType(@RequestBody SessionType newSessionType) {
        EntityModel<SessionType> entityModel = assembler.toModel(repository.save(newSessionType));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/sessionTypes/{id}")
    public EntityModel<SessionType> one(@PathVariable Long id) {
        SessionType sessionType = repository.findById(id) //
                .orElseThrow(() -> new ItemNotFoundException(id, "sessionType"));

        return assembler.toModel(sessionType);
    }

    @PutMapping("/sessionTypes/{id}")
    public ResponseEntity<EntityModel<SessionType>> replaceSessionType(@RequestBody SessionType newSessionType,
            @PathVariable Long id) {
        SessionType updatedSessionType = repository.findById(id) //
                .map(sessionType -> {
                    sessionType.setTypeName(newSessionType.getTypeName());
                    sessionType.setTypeDescription(newSessionType.getTypeDescription());
                    return repository.save(sessionType);
                }) //
                .orElseGet(() -> {
                    newSessionType.setId(id);
                    return repository.save(newSessionType);
                });

        EntityModel<SessionType> entityModel = assembler.toModel(updatedSessionType);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/sessionTypes/{id}")
    public ResponseEntity<?> deleteSessionType(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
