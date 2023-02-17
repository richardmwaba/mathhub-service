package com.hubformath.mathhubservice.controller.sis;

import java.util.List;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.hubformath.mathhubservice.assembler.sis.StudentModelAssembler;
import com.hubformath.mathhubservice.dto.sis.StudentDto;
import com.hubformath.mathhubservice.dto.sis.StudentRequestDto;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.service.sis.IStudentService;

@RestController
@RequestMapping(path = "/api/v1/sis")
public class StudentController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private StudentModelAssembler studentModelAssembler;

    public StudentController() {
        super();
    }

    @GetMapping("/students")
    public ResponseEntity<CollectionModel<EntityModel<StudentDto>>> getAllStudents() {
        List<StudentDto> students = studentService.getAllStudents().stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .toList();

        CollectionModel<EntityModel<StudentDto>> studentCollectionModel = studentModelAssembler
                .toCollectionModel(students);

        return ResponseEntity.ok().body(studentCollectionModel);
    }

    @PostMapping("/students")
    public ResponseEntity<EntityModel<StudentDto>> newStudent(
            @RequestBody StudentRequestDto studentRequestDto) {
        Student newStudent = studentService.createStudent(studentRequestDto);

        EntityModel<StudentDto> studentEntityModel = studentModelAssembler
                .toModel(modelMapper.map(newStudent, StudentDto.class));

        return ResponseEntity.created(studentEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(studentEntityModel);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<EntityModel<StudentDto>> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);

        EntityModel<StudentDto> studentEntityModel = studentModelAssembler
                .toModel(modelMapper.map(student, StudentDto.class));

        return ResponseEntity.ok().body(studentEntityModel);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<EntityModel<StudentDto>> replaceStudent(
            @RequestBody StudentDto studentDto,
            @PathVariable Long id) {
        Student studentRequest = modelMapper.map(studentDto, Student.class);
        Student updatedStudent = studentService.updateStudent(id, studentRequest);

        EntityModel<StudentDto> studentEntityModel = studentModelAssembler
                .toModel(modelMapper.map(updatedStudent, StudentDto.class));

        return ResponseEntity.ok().body(studentEntityModel);

    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);

        return ResponseEntity.ok().body("Student deleted successfully");
    }
}
