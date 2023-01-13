package com.hubformath.mathhubservice.assemblers.config;

import com.hubformath.mathhubservice.controllers.sis.SubjectController;
import com.hubformath.mathhubservice.dtos.config.SubjectDto;
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
public class SubjectModelAssembler
        implements RepresentationModelAssembler<SubjectDto, EntityModel<SubjectDto>> {

    @Override
    public  EntityModel<SubjectDto> toModel(SubjectDto subject){

        return EntityModel.of(subject,
                linkTo(methodOn(SubjectController.class).getSubjectById(subject.getId())).withSelfRel(),
                linkTo(methodOn(SubjectController.class).getAllSubjects()).withRel("subjects"));
    }

    @Override
    public CollectionModel<EntityModel<SubjectDto>> toCollectionModel(
            Iterable<? extends SubjectDto> subjects) {
        List<EntityModel<SubjectDto>> subjectList = StreamSupport.stream(subjects.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(subjectList, linkTo(methodOn(SubjectController.class)
                .getAllSubjects())
                .withSelfRel());
    }
}
