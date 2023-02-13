package com.hubformath.mathhubservice.assembler.systemconfig;

import com.hubformath.mathhubservice.controller.sis.GradeController;
import com.hubformath.mathhubservice.dto.systemconfig.GradeDto;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GradeModelAssembler
        implements RepresentationModelAssembler<GradeDto, EntityModel<GradeDto>> {

    @Override
    public  EntityModel<GradeDto> toModel(GradeDto grade){

        return EntityModel.of(grade,
                linkTo(methodOn(GradeController.class).getGradeById(grade.getId())).withSelfRel(),
                linkTo(methodOn(GradeController.class).getAllGrades()).withRel("grades"));
    }

    @Override
    public CollectionModel<EntityModel<GradeDto>> toCollectionModel(
            Iterable<? extends GradeDto> grades) {
        List<EntityModel<GradeDto>> gradeList = StreamSupport.stream(grades.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(gradeList, linkTo(methodOn(GradeController.class)
                .getAllGrades())
                .withSelfRel());
    }
}
