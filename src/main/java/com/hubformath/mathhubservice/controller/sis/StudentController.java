package com.hubformath.mathhubservice.controller.sis;

import com.hubformath.mathhubservice.model.sis.LessonRequest;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.sis.StudentActionBase;
import com.hubformath.mathhubservice.model.sis.StudentRequest;
import com.hubformath.mathhubservice.service.sis.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
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
import java.util.UUID;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/v1/sis")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(final StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER')")
    public ResponseEntity<CollectionModel<EntityModel<Student>>> getAllStudents() {
        CollectionModel<EntityModel<Student>> students = toCollectionModel(studentService.getAllStudents());

        return ResponseEntity.ok().body(students);
    }

    @PostMapping("/students")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER')")
    public ResponseEntity<EntityModel<Student>> newStudent(@RequestBody final StudentRequest studentRequest) {
        EntityModel<Student> newStudent = toModel(studentService.createStudent(studentRequest));
        return ResponseEntity.created(newStudent.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(newStudent);
    }

    @GetMapping("/students/{studentId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<Student>> getStudentById(@PathVariable final String studentId) {
        try {
            EntityModel<Student> student = toModel(studentService.getStudentById(studentId));
            return ResponseEntity.ok().body(student);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/students/{studentId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<Student>> replaceStudent(@RequestBody final Student studentRequest,
                                                                      @PathVariable final String studentId) {
        try {
            EntityModel<Student> updatedStudent = toModel(studentService.updateStudent(studentId, studentRequest));
            return ResponseEntity.ok().body(updatedStudent);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/students/{studentId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER')")
    public ResponseEntity<String> deleteStudent(@PathVariable final String studentId) {
        try {
            studentService.deleteStudent(studentId);
            return ResponseEntity.ok().body("Student deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/students/{studentId}/lessons")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<Student>> addStudentLesson(@PathVariable final String studentId,
                                                                        @RequestBody final LessonRequest lessonRequest) {
        try {
            EntityModel<Student> student = toModel(studentService.addLessonToStudent(studentId, lessonRequest));
            return ResponseEntity.ok(student);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("students/{studentId}/actions")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<StudentActionBase>> executeStudentAction(@PathVariable final UUID studentId,
                                                                               @RequestBody final StudentActionBase studentActionBase) {
        return ResponseEntity.noContent().build();
    }

    private EntityModel<Student> toModel(final Student student) {
        return EntityModel.of(student,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                                                        .getStudentById(student.getStudentId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                                                        .getAllStudents()).withRel("students"));
    }

    private CollectionModel<EntityModel<Student>> toCollectionModel(final Iterable<? extends Student> studentsIterable) {
        List<EntityModel<Student>> students = StreamSupport.stream(studentsIterable.spliterator(), false)
                                                                     .map(this::toModel)
                                                                     .toList();

        return CollectionModel.of(students,
                                  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                                                            .getAllStudents())
                                                   .withSelfRel());
    }
}