package com.hubformath.mathhubservice.assembler.systemconfig;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controller.systemconfig.AssessmentTypeController;
import com.hubformath.mathhubservice.dto.systemconfig.AssessmentTypeDto;

@Component
public class AssessmentTypeModelAssembler
        implements RepresentationModelAssembler<AssessmentTypeDto, EntityModel<AssessmentTypeDto>> {
    @Override
    public EntityModel<AssessmentTypeDto> toModel(AssessmentTypeDto assessmentType) {
        return EntityModel.of(assessmentType,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentTypeController.class).getAssessmentTypeById(assessmentType.getId()))
                        .withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentTypeController.class).getAllAssessmentTypes()).withRel("assessmentTypes"));
    }

    @Override
    public CollectionModel<EntityModel<AssessmentTypeDto>> toCollectionModel(
            Iterable<? extends AssessmentTypeDto> assessmentTypes) {
        List<EntityModel<AssessmentTypeDto>> assessmentTypeList = StreamSupport
                .stream(assessmentTypes.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(assessmentTypeList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentTypeController.class)
                .getAllAssessmentTypes())
                .withSelfRel());
    }

}
