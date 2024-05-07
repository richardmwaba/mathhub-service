package com.hubformath.mathhubservice.controller.sis;

import com.hubformath.mathhubservice.controller.util.CollectionModelUtils;
import com.hubformath.mathhubservice.model.sis.Parent;
import com.hubformath.mathhubservice.service.sis.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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

@RestController
@RequestMapping(path = "/api/v1/sis")
public class ParentController {

    private final ParentService parentService;

    @Autowired
    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping("/parents")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<CollectionModel<?>> getAllParents() {
        List<Parent> parents = parentService.getAllParents();
        return ResponseEntity.ok().body(toCollectionModel(parents));
    }

    @GetMapping("/parents/{parentId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<Parent>> getParentById(@PathVariable String parentId) {
        try {
            EntityModel<Parent> parent = toModel(parentService.getParentById(parentId));
            return ResponseEntity.ok().body(parent);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/parents/{parentId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<Parent>> replaceParent(@RequestBody Parent parentRequest,
                                                             @PathVariable String parentId) {
        try {
            EntityModel<Parent> updatedParent = toModel(parentService.updateParent(parentId, parentRequest));
            return ResponseEntity.ok().body(updatedParent);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/parents/{parentId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('STUDENT')")
    public ResponseEntity<String> deleteParent(@PathVariable String parentId) {
        try {
            parentService.deleteParent(parentId);
            return ResponseEntity.ok().body("Parent deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public EntityModel<Parent> toModel(Parent parent) {
        return EntityModel.of(parent,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ParentController.class)
                                                                        .getParentById(parent.getParentId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ParentController.class)
                                                                        .getAllParents()).withRel("parents"));
    }

    private CollectionModel<?> toCollectionModel(List<Parent> parentList) {
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ParentController.class)
                                                              .getAllParents())
                                     .withRel("parents");
        List<EntityModel<Parent>> parents = parentList.stream()
                                                      .map(this::toModel)
                                                      .toList();

        return parentList.isEmpty()
                ? CollectionModelUtils.getEmptyEmbeddedCollectionModel(Parent.class, link)
                : CollectionModel.of(parents, link);
    }
}
