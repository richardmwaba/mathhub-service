package com.hubformath.mathhubservice.controller.sis;

import com.hubformath.mathhubservice.controller.util.CollectionModelUtils;
import com.hubformath.mathhubservice.model.sis.LessonRequest;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.sis.StudentActionBase;
import com.hubformath.mathhubservice.model.sis.StudentRequest;
import com.hubformath.mathhubservice.service.sis.StudentService;
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
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/sis")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER')")
    public ResponseEntity<CollectionModel<?>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok().body(toCollectionModel(students));
    }

    @PostMapping("/students")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER')")
    public ResponseEntity<EntityModel<Student>> newStudent(@RequestBody StudentRequest studentRequest) {
        EntityModel<Student> newStudent = toModel(studentService.createStudent(studentRequest));
        return ResponseEntity.created(newStudent.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(newStudent);
    }

    @GetMapping("/students/{studentId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<Student>> getStudentById(@PathVariable String studentId) {
        try {
            EntityModel<Student> student = toModel(studentService.getStudentById(studentId));
            return ResponseEntity.ok().body(student);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/students/{studentId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<Student>> replaceStudent(@RequestBody Student studentRequest,
                                                               @PathVariable String studentId) {
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
    public ResponseEntity<EntityModel<Student>> addStudentLesson(@PathVariable String studentId,
                                                                 @RequestBody LessonRequest lessonRequest) {
        try {
            EntityModel<Student> student = toModel(studentService.addLessonToStudent(studentId, lessonRequest));
            return ResponseEntity.ok(student);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("students/{studentId}/actions")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<StudentActionBase>> executeStudentAction(@PathVariable UUID studentId,
                                                                               @RequestBody StudentActionBase studentActionBase) {
        return ResponseEntity.noContent().build();
    }

    private EntityModel<Student> toModel(Student student) {
        return EntityModel.of(student,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                                                        .getStudentById(student.getStudentId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                                                        .getAllStudents()).withRel("students"));
    }

    private CollectionModel<?> toCollectionModel(List<Student> studentList) {
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                                              .getAllStudents()).withSelfRel();
        List<EntityModel<Student>> students = studentList.stream()
                                                         .map(this::toModel)
                                                         .toList();

        return studentList.isEmpty()
                ? CollectionModelUtils.getEmptyEmbeddedCollectionModel(Student.class, link)
                : CollectionModel.of(students, link);
    }
}