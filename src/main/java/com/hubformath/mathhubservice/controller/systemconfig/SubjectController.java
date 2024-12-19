package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.controller.util.CollectionModelUtils;
import com.hubformath.mathhubservice.model.sis.SubjectRequest;
import com.hubformath.mathhubservice.model.systemconfig.Subject;
import com.hubformath.mathhubservice.service.systemconfig.SubjectService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(path = "/api/v1/sis")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/subjects")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER') or hasRole('TEACHER')")
    public ResponseEntity<CollectionModel<?>> getAllSubjects(@RequestParam(name = "grade", required = false) List<String> grades) {
        return ResponseEntity.ok().body(toCollectionModel(subjectService.getAllSubjects(grades), grades));
    }

    @PostMapping("/subjects")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER')")
    public ResponseEntity<Object> newSubject(@RequestBody SubjectRequest subjectRequest) {
        try {
            Subject newSubject = subjectService.createSubject(subjectRequest);
            EntityModel<Subject> subjectDtoEntityModel = toModel(newSubject, Collections.emptyList());

            return ResponseEntity.created(subjectDtoEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                                 body(subjectDtoEntityModel);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/subjects/{subjectId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER') or hasRole('TEACHER')")
    public ResponseEntity<EntityModel<Subject>> getSubjectById(@PathVariable String subjectId) {
        try {
            Subject subject = subjectService.getSubjectById(subjectId);
            EntityModel<Subject> subjectEntityModel = toModel(subject, Collections.emptyList());

            return ResponseEntity.ok().body(subjectEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/subjects/{subjectId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER')")
    public ResponseEntity<EntityModel<Subject>> updateSubject(@RequestBody SubjectRequest subjectRequest,
                                                              @PathVariable String subjectId) {
        try {
            Subject updatedSubject = subjectService.updateSubject(subjectId, subjectRequest);
            EntityModel<Subject> subjectEntityModel = toModel(updatedSubject, Collections.emptyList());

            return ResponseEntity.ok().body(subjectEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/subjects/{subjectId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER')")
    public ResponseEntity<String> deleteSubject(@PathVariable String subjectId) {
        try {
            subjectService.deleteSubject(subjectId);
            return ResponseEntity.ok().body("Subject deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<Subject> toModel(Subject subject, List<String> grades) {
        return EntityModel.of(subject,
                              linkTo(methodOn(SubjectController.class).getSubjectById(subject.getId())).withSelfRel(),
                              linkTo(methodOn(SubjectController.class).getAllSubjects(grades)).withRel("all"));
    }

    private CollectionModel<?> toCollectionModel(List<Subject> subjectList, List<String> grades) {
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SubjectController.class)
                                                              .getAllSubjects(grades)).withSelfRel();

        List<EntityModel<Subject>> subjects = subjectList.stream()
                                                         .map(subject -> toModel(subject, grades))
                                                         .toList();

        return subjectList.isEmpty()
                ? CollectionModelUtils.getEmptyEmbeddedCollectionModel(Subject.class, link)
                : CollectionModel.of(subjects, link);
    }
}
