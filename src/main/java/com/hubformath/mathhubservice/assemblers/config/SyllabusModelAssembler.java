package com.hubformath.mathhubservice.assemblers.config;

import com.hubformath.mathhubservice.controllers.config.SyllabusController;
import com.hubformath.mathhubservice.dtos.config.SyllabusDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class SyllabusModelAssembler implements RepresentationModelAssembler<SyllabusDto, EntityModel<SyllabusDto>> {
    @Override
    public EntityModel<SyllabusDto> toModel(SyllabusDto syllabus) {
        return EntityModel.of(syllabus,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SyllabusController.class).getSyllabusById(syllabus.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SyllabusController.class).getAllSyllabi()).withRel("syllabus"));
    }

    @Override
    public CollectionModel<EntityModel<SyllabusDto>> toCollectionModel(
            Iterable<? extends SyllabusDto> syllabus) {
        List<EntityModel<SyllabusDto>> syllabusList = StreamSupport.stream(syllabus.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(syllabusList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SyllabusController.class)
                .getAllSyllabi())
                .withSelfRel());
    }
}
