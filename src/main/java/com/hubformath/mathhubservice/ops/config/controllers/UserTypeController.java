package com.hubformath.mathhubservice.ops.config.controllers;

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

import com.hubformath.mathhubservice.ops.config.assemblers.UserTypeModelAssembler;
import com.hubformath.mathhubservice.ops.config.models.UserType;
import com.hubformath.mathhubservice.ops.config.repositories.UserTypeRepository;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@RestController
@RequestMapping(path="/v1/api/ops")
public class UserTypeController {
    private final UserTypeRepository repository;
    private final UserTypeModelAssembler assembler;

    public UserTypeController(UserTypeRepository repository, UserTypeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/userTypes")
    public CollectionModel<EntityModel<UserType>> all() {
        List<EntityModel<UserType>> userTypes = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(userTypes, linkTo(methodOn(UserTypeController.class).all()).withSelfRel());
    }

    @PostMapping("/userTypes")
    public ResponseEntity<EntityModel<UserType>> newUserType(@RequestBody UserType newUserType) {
        EntityModel<UserType> entityModel = assembler.toModel(repository.save(newUserType));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/userTypes/{id}")
    public EntityModel<UserType> one(@PathVariable Long id) {
        UserType userType = repository.findById(id) //
                .orElseThrow(() -> new ItemNotFoundException(id, "userType"));

        return assembler.toModel(userType);
    }

    @PutMapping("/userTypes/{id}")
    public ResponseEntity<EntityModel<UserType>> replaceUserType(@RequestBody UserType newUserType,
            @PathVariable Long id) {
        UserType updatedUserType = repository.findById(id) //
                .map(userType -> {
                    userType.setTypeName(newUserType.getTypeName());
                    userType.setTypeDescription(newUserType.getTypeDescription());
                    return repository.save(userType);
                }) //
                .orElseGet(() -> {
                    newUserType.setId(id);
                    return repository.save(newUserType);
                });

        EntityModel<UserType> entityModel = assembler.toModel(updatedUserType);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/userTypes/{id}")
    public ResponseEntity<?> deleteUserType(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
