package com.hubformath.mathhubservice.assemblers.config;

import com.hubformath.mathhubservice.controllers.config.SyllabusController;
import com.hubformath.mathhubservice.dtos.config.SyllabusDto;
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
public class SyllabusModelAssembler implements RepresentationModelAssembler<SyllabusDto, EntityModel<SyllabusDto>> {
    @Override
    public EntityModel<SyllabusDto> toModel(SyllabusDto syllabus) {

        return EntityModel.of(syllabus,
                linkTo(methodOn(SyllabusController.class).getSyllabusById(syllabus.getId())).withSelfRel(),
                linkTo(methodOn(SyllabusController.class).getAllSyllabus()).withRel("syllabus"));
    }

    @Override
    public CollectionModel<EntityModel<SyllabusDto>> toCollectionModel(
            Iterable<? extends SyllabusDto> syllabus) {
        List<EntityModel<SyllabusDto>> syllabusList = StreamSupport.stream(syllabus.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(syllabusList, linkTo(methodOn(SyllabusController.class)
                .getAllSyllabus())
                .withSelfRel());
    }
}
