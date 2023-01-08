package com.hubformath.mathhubservice.assemblers.config;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.config.AssessmentTypeController;
import com.hubformath.mathhubservice.dtos.config.AssessmentTypeDto;

@Component
public class AssessmentTypeModelAssembler
        implements RepresentationModelAssembler<AssessmentTypeDto, EntityModel<AssessmentTypeDto>> {
    @Override
    public EntityModel<AssessmentTypeDto> toModel(AssessmentTypeDto assessmentType) {

        return EntityModel.of(assessmentType,
                linkTo(methodOn(AssessmentTypeController.class).getAssessmentTypeById(assessmentType.getId()))
                        .withSelfRel(),
                linkTo(methodOn(AssessmentTypeController.class).getAllAssessmentTypes()).withRel("assessmentTypes"));
    }

    @Override
    public CollectionModel<EntityModel<AssessmentTypeDto>> toCollectionModel(
            Iterable<? extends AssessmentTypeDto> assessmentTypes) {
        List<EntityModel<AssessmentTypeDto>> assessmentTypeList = StreamSupport
                .stream(assessmentTypes.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(assessmentTypeList, linkTo(methodOn(AssessmentTypeController.class)
                .getAllAssessmentTypes())
                .withSelfRel());
    }

}
