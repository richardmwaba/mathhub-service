package com.hubformath.mathhubservice.controller.sis;

import com.hubformath.mathhubservice.controller.util.CollectionModelUtils;
import com.hubformath.mathhubservice.model.systemconfig.Grade;
import com.hubformath.mathhubservice.service.systemconfig.GradeService;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(path = "/api/v1/sis")
public class GradeController {

    private final GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("/grades")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<CollectionModel<?>> getAllGrades() {
        List<Grade> grades = gradeService.getAllGrades();
        return ResponseEntity.ok().body(toCollectionModel(grades));
    }

    @PostMapping("/grades")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<EntityModel<Grade>> newGrade(@RequestBody Grade gradeRequest) {
        EntityModel<Grade> newGrade = toModel(gradeService.createGrade(gradeRequest));
        return ResponseEntity.created(newGrade.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                             body(newGrade);
    }

    @GetMapping("/grades/{gradeId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<Grade>> getGradeById(@PathVariable String gradeId) {
        try {
            EntityModel<Grade> grade = toModel(gradeService.getGradeById(gradeId));
            return ResponseEntity.ok().body(grade);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/grades/{gradeId}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<EntityModel<Grade>> replaceGrade(@RequestBody Grade gradeRequest,
                                                           @PathVariable String gradeId) {
        try {
            EntityModel<Grade> updatedGrade = toModel(gradeService.updateGrade(gradeId, gradeRequest));
            return ResponseEntity.ok().body(updatedGrade);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/grades/{gradeId}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<String> deleteGrade(@PathVariable String gradeId) {
        try {
            gradeService.deleteGrade(gradeId);
            return ResponseEntity.ok().body("Grade deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<Grade> toModel(Grade grade) {
        return EntityModel.of(grade,
                              linkTo(methodOn(GradeController.class).getGradeById(grade.getId())).withSelfRel(),
                              linkTo(methodOn(GradeController.class).getAllGrades()).withRel("grades"));
    }

    private CollectionModel<?> toCollectionModel(List<Grade> gradeList) {
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GradeController.class).getAllGrades())
                                     .withSelfRel();
        List<EntityModel<Grade>> grades = gradeList.stream()
                                                       .map(this::toModel)
                                                       .toList();

        return gradeList.isEmpty()
                ? CollectionModelUtils.getEmptyEmbeddedCollectionModel(Grade.class, link)
                : CollectionModel.of(grades, link);
    }
}
