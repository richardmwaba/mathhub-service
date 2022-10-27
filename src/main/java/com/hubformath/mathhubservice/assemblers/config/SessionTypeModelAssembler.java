package com.hubformath.mathhubservice.assemblers.config;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.config.SessionTypeController;
import com.hubformath.mathhubservice.models.config.SessionType;

@Component
public class SessionTypeModelAssembler implements RepresentationModelAssembler<SessionType, EntityModel<SessionType>> {
    @Override
    public EntityModel<SessionType> toModel(SessionType sessionType) {

    return EntityModel.of(sessionType, //
        linkTo(methodOn(SessionTypeController.class).one(sessionType.getId())).withSelfRel(),
        linkTo(methodOn(SessionTypeController.class).all()).withRel("sessionTypes"));
  }
}