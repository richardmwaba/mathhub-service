package com.hubformath.mathhubservice.assembler.sis;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controller.sis.StudentController;
import com.hubformath.mathhubservice.dto.sis.StudentDto;

@Component
public class StudentModelAssembler implements RepresentationModelAssembler<StudentDto, EntityModel<StudentDto>> {
    @Override
    public EntityModel<StudentDto> toModel(StudentDto student) {
        return EntityModel.of(student,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class).getStudentById(student.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class).getAllStudents()).withRel("students"));
    }

    @Override
    public CollectionModel<EntityModel<StudentDto>> toCollectionModel(
            Iterable<? extends StudentDto> students) {
        List<EntityModel<StudentDto>> studentList = StreamSupport.stream(students.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(studentList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                .getAllStudents())
                .withSelfRel());
    }
}
