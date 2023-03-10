package com.hubformath.mathhubservice.controller.sis;

import java.util.List;
import java.util.stream.StreamSupport;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.systemconfig.LessonDto;
import com.hubformath.mathhubservice.model.sis.Lesson;
import com.hubformath.mathhubservice.model.systemconfig.Subject;
import com.hubformath.mathhubservice.service.sis.StudentService;
import com.hubformath.mathhubservice.service.systemconfig.SubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hubformath.mathhubservice.dto.sis.StudentDto;
import com.hubformath.mathhubservice.dto.sis.StudentRequestDto;
import com.hubformath.mathhubservice.model.sis.Student;

@RestController
@RequestMapping(path = "/api/v1/sis")
public class StudentController {

    private final ModelMapper modelMapper;

    private final StudentService studentService;

    private final SubjectService subjectService;

    @Autowired
    public StudentController(final ModelMapperConfig modelMapperConfig, final StudentService studentService, final SubjectService subjectService) {
        this.modelMapper = modelMapperConfig.createModelMapper();
        this.studentService = studentService;
        this.subjectService = subjectService;
    }

    @GetMapping("/students")
    public ResponseEntity<CollectionModel<EntityModel<StudentDto>>> getAllStudents() {
        List<StudentDto> students = studentService.getAllStudents().stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .toList();
        CollectionModel<EntityModel<StudentDto>> studentCollectionModel = toCollectionModel(students);

        return ResponseEntity.ok().body(studentCollectionModel);
    }

    @PostMapping("/students")
    public ResponseEntity<EntityModel<StudentDto>> newStudent(@RequestBody final StudentRequestDto studentRequestDto) {
        Student newStudent = studentService.createStudent(studentRequestDto);
        EntityModel<StudentDto> studentEntityModel = toModel(modelMapper.map(newStudent, StudentDto.class));

        return ResponseEntity.created(studentEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(studentEntityModel);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<EntityModel<StudentDto>> getStudentById(@PathVariable final Long id) {
        Student student = studentService.getStudentById(id);
        EntityModel<StudentDto> studentEntityModel = toModel(modelMapper.map(student, StudentDto.class));

        return ResponseEntity.ok().body(studentEntityModel);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<EntityModel<StudentDto>> replaceStudent(
            @RequestBody final StudentDto studentDto,
            @PathVariable final Long id) {
        Student studentRequest = modelMapper.map(studentDto, Student.class);
        Student updatedStudent = studentService.updateStudent(id, studentRequest);
        EntityModel<StudentDto> studentEntityModel = toModel(modelMapper.map(updatedStudent, StudentDto.class));

        return ResponseEntity.ok().body(studentEntityModel);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable final Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().body("Student deleted successfully");
    }

    @PostMapping("/students/{id}/lessons")
    public ResponseEntity<EntityModel<StudentDto>> addStudentLesson(@PathVariable final Long id, @RequestBody final LessonDto lesson) {
        Lesson newlesson = modelMapper.map(lesson, Lesson.class);
        Subject subject = subjectService.getSubjectById(lesson.getSubjectId());
        newlesson.setSubject(subject);
        Student student = studentService.addLessonToStudent(id, newlesson);

        EntityModel<StudentDto> studentEntityModel = toModel(modelMapper.map(student, StudentDto.class));
        return ResponseEntity.ok(studentEntityModel);
    }

    private EntityModel<StudentDto> toModel(final StudentDto student) {
        return EntityModel.of(student,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class).getStudentById(student.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class).getAllStudents()).withRel("students"));
    }

    private CollectionModel<EntityModel<StudentDto>> toCollectionModel(final Iterable<? extends StudentDto> students) {
        List<EntityModel<StudentDto>> studentList = StreamSupport.stream(students.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(studentList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                        .getAllStudents())
                .withSelfRel());
    }
}
