package com.hubformath.mathhubservice.assemblers.config;

import com.hubformath.mathhubservice.controllers.sis.SubjectController;
import com.hubformath.mathhubservice.dtos.config.SubjectDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class SubjectModelAssembler
        implements RepresentationModelAssembler<SubjectDto, EntityModel<SubjectDto>> {

    @Override
    public  EntityModel<SubjectDto> toModel(SubjectDto subject){

        return EntityModel.of(subject,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SubjectController.class).getSubjectById(subject.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SubjectController.class).getAllSubjects()).withRel("subjects"));
    }

    @Override
    public CollectionModel<EntityModel<SubjectDto>> toCollectionModel(
            Iterable<? extends SubjectDto> subjects) {
        List<EntityModel<SubjectDto>> subjectList = StreamSupport.stream(subjects.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(subjectList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SubjectController.class)
                .getAllSubjects())
                .withSelfRel());
    }
}
