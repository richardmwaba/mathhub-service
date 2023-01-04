package com.hubformath.mathhubservice.assemblers.sis;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.sis.StudentController;
import com.hubformath.mathhubservice.dtos.sis.StudentDto;

@Component
public class StudentModelAssembler
        implements RepresentationModelAssembler<StudentDto, EntityModel<StudentDto>> {
    @Override
    public EntityModel<StudentDto> toModel(StudentDto student) {

        return EntityModel.of(student,
                linkTo(methodOn(StudentController.class).getStudentById(student.getId())).withSelfRel(),
                linkTo(methodOn(StudentController.class).getAllStudents()).withRel("students"));
    }

    @Override
    public CollectionModel<EntityModel<StudentDto>> toCollectionModel(
            Iterable<? extends StudentDto> students) {
        List<EntityModel<StudentDto>> studentList = StreamSupport.stream(students.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(studentList, linkTo(methodOn(StudentController.class)
                .getAllStudents())
                .withSelfRel());
    }
}
