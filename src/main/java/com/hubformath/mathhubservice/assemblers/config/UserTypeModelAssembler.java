package com.hubformath.mathhubservice.assemblers.config;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.config.UserTypeController;
import com.hubformath.mathhubservice.models.config.UserType;

@Component
public class UserTypeModelAssembler implements RepresentationModelAssembler<UserType, EntityModel<UserType>> {
  @Override
  public EntityModel<UserType> toModel(UserType userType) {

    return EntityModel.of(userType, //
        linkTo(methodOn(UserTypeController.class).one(userType.getId())).withSelfRel(),
        linkTo(methodOn(UserTypeController.class).all()).withRel("userTypes"));
  }
}