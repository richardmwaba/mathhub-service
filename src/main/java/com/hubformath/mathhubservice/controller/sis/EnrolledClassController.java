package com.hubformath.mathhubservice.controller.sis;

import com.hubformath.mathhubservice.controller.util.CollectionModelUtils;
import com.hubformath.mathhubservice.model.sis.EnrolledClass;
import com.hubformath.mathhubservice.model.sis.EnrolledClassRequest;
import com.hubformath.mathhubservice.service.sis.EnrolledClassService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/api/v1/sis/students")
public class EnrolledClassController {

    private final EnrolledClassService enrolledClassService;

    @Autowired
    public EnrolledClassController(EnrolledClassService enrolledClassService) {
        this.enrolledClassService = enrolledClassService;
    }

    @PostMapping("/{studentId}/classes")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<?> addStudentsClasses(@PathVariable String studentId,
                                                                 @RequestBody List<EnrolledClassRequest> enrolledClassRequests) {
        try {
            List<EnrolledClass> enrolledClasses = enrolledClassService.addClassesToStudent(studentId,
                                                                                           enrolledClassRequests);
            return ResponseEntity.ok(toClassCollectionModelWithLinks(enrolledClasses, studentId, true));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{studentId}/classes/{classId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<?>> getStudentsClassByClassId(@PathVariable String studentId,
                                                                    @PathVariable String classId) {
        try {
            EnrolledClass enrolledClass = enrolledClassService.getStudentsClassByClassId(studentId, classId);
            return ResponseEntity.ok().body(toClassModelWithLinks(enrolledClass, studentId, true));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{studentId}/classes")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<CollectionModel<?>> getAllClassesForStudent(@PathVariable String studentId,
                                                                      @RequestParam(required = false, defaultValue = "true") boolean onlyActiveClasses) {
        try {
            List<EnrolledClass> enrolledClasses = enrolledClassService.getAllClassesForStudent(studentId, onlyActiveClasses);
            return ResponseEntity.ok()
                                 .body(toClassCollectionModelWithLinks(enrolledClasses, studentId, onlyActiveClasses));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{studentId}/classes/{classId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<?>> updateStudentsClass(@PathVariable String studentId,
                                                              @PathVariable String classId,
                                                              @RequestBody EnrolledClassRequest enrolledClassRequest) {
        try {
            EnrolledClass enrolledClass = enrolledClassService.updateEnrolledClass(classId, enrolledClassRequest);
            return ResponseEntity.ok().body(toClassModelWithLinks(enrolledClass, studentId, true));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{studentId}/classes/{classId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<String> cancelStudentsClass(@PathVariable String studentId, @PathVariable String classId) {
        try {
            enrolledClassService.cancelStudentsClass(studentId, classId);
            return ResponseEntity.ok().body("Class cancelled successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<EnrolledClass> toClassModelWithLinks(EnrolledClass enrolledClass, String studentId, boolean onlyActiveClasses) {
        return EntityModel.of(enrolledClass,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnrolledClassController.class)
                                                                        .getStudentsClassByClassId(studentId,
                                                                                                   enrolledClass.getId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnrolledClassController.class)
                                                                        .getAllClassesForStudent(studentId, onlyActiveClasses))
                                               .withRel("all"));
    }

    private CollectionModel<?> toClassCollectionModelWithLinks(List<EnrolledClass> classesList, String studentId, boolean onlyActiveClasses) {
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnrolledClassController.class)
                                                              .getAllClassesForStudent(studentId, onlyActiveClasses)).withSelfRel();
        List<EntityModel<EnrolledClass>> classes = classesList.stream()
                                                              .map(aClass -> toClassModelWithLinks(aClass, studentId, onlyActiveClasses))
                                                              .toList();

        return classesList.isEmpty()
                ? CollectionModelUtils.getEmptyEmbeddedCollectionModel(EnrolledClass.class, link)
                : CollectionModel.of(classes, link);
    }
}
