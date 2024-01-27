package com.hubformath.mathhubservice.controller.sis;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.sis.LessonDto;
import com.hubformath.mathhubservice.dto.sis.StudentDto;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.sis.StudentActionBase;
import com.hubformath.mathhubservice.service.sis.StudentService;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper;

    private final StudentService studentService;

    @Autowired
    public StudentController(final ModelMapperConfig modelMapperConfig, final StudentService studentService) {
        this.modelMapper = modelMapperConfig.createModelMapper();
        this.studentService = studentService;
    }

    @GetMapping("/students")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER')")
    public ResponseEntity<CollectionModel<EntityModel<StudentDto>>> getAllStudents() {
        List<StudentDto> students = studentService.getAllStudents().stream()
                                                  .map(student -> modelMapper.map(student, StudentDto.class))
                                                  .toList();
        CollectionModel<EntityModel<StudentDto>> studentCollectionModel = toCollectionModel(students);

        return ResponseEntity.ok().body(studentCollectionModel);
    }

    @PostMapping("/students")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER')")
    public ResponseEntity<EntityModel<StudentDto>> newStudent(@RequestBody final StudentDto studentRequest) {
        Student newStudent = studentService.createStudent(studentRequest);
        EntityModel<StudentDto> studentEntityModel = toModel(modelMapper.map(newStudent, StudentDto.class));

        return ResponseEntity.created(studentEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(studentEntityModel);
    }

    @GetMapping("/students/{studentId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<StudentDto>> getStudentById(@PathVariable final UUID studentId) {
        try {
            Student student = studentService.getStudentById(studentId);
            EntityModel<StudentDto> studentEntityModel = toModel(modelMapper.map(student, StudentDto.class));
            return ResponseEntity.ok().body(studentEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/students/{studentId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<StudentDto>> replaceStudent(@RequestBody final StudentDto studentDto,
                                                                  @PathVariable final UUID studentId) {
        try {
            Student studentRequest = modelMapper.map(studentDto, Student.class);
            Student updatedStudent = studentService.updateStudent(studentId, studentRequest);
            EntityModel<StudentDto> studentEntityModel = toModel(modelMapper.map(updatedStudent, StudentDto.class));
            return ResponseEntity.ok().body(studentEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/students/{studentId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER')")
    public ResponseEntity<String> deleteStudent(@PathVariable final UUID studentId) {
        try {
            studentService.deleteStudent(studentId);
            return ResponseEntity.ok().body("Student deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/students/{studentId}/lessons")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<StudentDto>> addStudentLesson(@PathVariable final UUID studentId,
                                                                    @RequestBody final LessonDto lessonRequest) {
        try {
            Student student = studentService.addLessonToStudent(studentId, lessonRequest);
            EntityModel<StudentDto> studentEntityModel = toModel(modelMapper.map(student, StudentDto.class));
            return ResponseEntity.ok(studentEntityModel);
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

    private EntityModel<StudentDto> toModel(final StudentDto student) {
        return EntityModel.of(student,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                                                        .getStudentById(student.getStudentId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                                                        .getAllStudents()).withRel("students"));
    }

    private CollectionModel<EntityModel<StudentDto>> toCollectionModel(final Iterable<? extends StudentDto> students) {
        List<EntityModel<StudentDto>> studentList = StreamSupport.stream(students.spliterator(), false)
                                                                 .map(this::toModel)
                                                                 .toList();

        return CollectionModel.of(studentList,
                                  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                                                            .getAllStudents())
                                                   .withSelfRel());
    }
}