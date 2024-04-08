package com.hubformath.mathhubservice.controller.sis;

import com.hubformath.mathhubservice.model.sis.Parent;
import com.hubformath.mathhubservice.service.sis.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/v1/sis")
public class ParentController {

    private final ParentService parentService;

    @Autowired
    public ParentController(final ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping("/parents")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<CollectionModel<EntityModel<Parent>>> getAllParents() {
        CollectionModel<EntityModel<Parent>> parents = toCollectionModel(parentService.getAllParents());
        return ResponseEntity.ok().body(parents);
    }

    @GetMapping("/parents/{parentId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<Parent>> getParentById(@PathVariable final String parentId) {
        try {
            EntityModel<Parent> parent = toModel(parentService.getParentById(parentId));
            return ResponseEntity.ok().body(parent);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/parents/{parentId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<Parent>> replaceParent(@RequestBody final Parent parentRequest,
                                                                @PathVariable final String parentId) {
        try {
            EntityModel<Parent> updatedParent = toModel(parentService.updateParent(parentId, parentRequest));
            return ResponseEntity.ok().body(updatedParent);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/parents/{parentId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('STUDENT')")
    public ResponseEntity<String> deleteParent(@PathVariable final String parentId) {
        try {
            parentService.deleteParent(parentId);
            return ResponseEntity.ok().body("Parent deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public EntityModel<Parent> toModel(final Parent parent) {
        return EntityModel.of(parent,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ParentController.class)
                                                                        .getParentById(parent.getParentId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ParentController.class)
                                                                        .getAllParents()).withRel("parents"));
    }

    private CollectionModel<EntityModel<Parent>> toCollectionModel(final Iterable<? extends Parent> parentsIterable) {
        List<EntityModel<Parent>> parents = StreamSupport.stream(parentsIterable.spliterator(), false)
                                                               .map(this::toModel)
                                                               .toList();

        return CollectionModel.of(parents,
                                  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ParentController.class)
                                                                            .getAllParents())
                                                   .withRel("parents"));
    }
}
