package com.hubformath.mathhubservice.assemblers.config;

import com.hubformath.mathhubservice.controllers.sis.GradeController;
import com.hubformath.mathhubservice.dtos.config.GradeDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class GradeModelAssembler implements RepresentationModelAssembler<GradeDto, EntityModel<GradeDto>> {

    @Override
    public  EntityModel<GradeDto> toModel(GradeDto grade){
        return EntityModel.of(grade,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GradeController.class).getGradeById(grade.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GradeController.class).getAllGrades()).withRel("grades"));
    }

    @Override
    public CollectionModel<EntityModel<GradeDto>> toCollectionModel(
            Iterable<? extends GradeDto> grades) {
        List<EntityModel<GradeDto>> gradeList = StreamSupport.stream(grades.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(gradeList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GradeController.class)
                .getAllGrades())
                .withSelfRel());
    }
}
