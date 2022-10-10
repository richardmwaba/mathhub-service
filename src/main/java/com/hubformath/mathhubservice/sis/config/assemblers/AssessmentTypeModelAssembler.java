package com.hubformath.mathhubservice.sis.config.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.sis.config.models.AssessmentType;
import com.hubformath.mathhubservice.sis.config.controllers.AssessmentTypeController;

@Component
public class AssessmentTypeModelAssembler implements RepresentationModelAssembler<AssessmentType, EntityModel<AssessmentType>> {
    @Override
    public EntityModel<AssessmentType> toModel(AssessmentType assessmentType) {

    return EntityModel.of(assessmentType, //
        linkTo(methodOn(AssessmentTypeController.class).one(assessmentType.getId())).withSelfRel(),
        linkTo(methodOn(AssessmentTypeController.class).all()).withRel("assessmentTypes"));
  }
}

