package com.hubformath.mathhubservice.assemblers.config;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.config.AssessmentTypeController;
import com.hubformath.mathhubservice.models.config.AssessmentType;

@Component
public class AssessmentTypeModelAssembler implements RepresentationModelAssembler<AssessmentType, EntityModel<AssessmentType>> {
    @Override
    public EntityModel<AssessmentType> toModel(AssessmentType assessmentType) {

    return EntityModel.of(assessmentType, //
        linkTo(methodOn(AssessmentTypeController.class).one(assessmentType.getId())).withSelfRel(),
        linkTo(methodOn(AssessmentTypeController.class).all()).withRel("assessmentTypes"));
  }
}

