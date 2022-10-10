package com.hubformath.mathhubservice.sis.config.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.sis.config.models.SessionType;
import com.hubformath.mathhubservice.sis.config.controllers.SessionTypeController;

@Component
public class SessionTypeModelAssembler implements RepresentationModelAssembler<SessionType, EntityModel<SessionType>> {
    @Override
    public EntityModel<SessionType> toModel(SessionType sessionType) {

    return EntityModel.of(sessionType, //
        linkTo(methodOn(SessionTypeController.class).one(sessionType.getId())).withSelfRel(),
        linkTo(methodOn(SessionTypeController.class).all()).withRel("sessionTypes"));
  }
}