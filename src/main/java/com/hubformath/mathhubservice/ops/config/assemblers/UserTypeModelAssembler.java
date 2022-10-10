package com.hubformath.mathhubservice.ops.config.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.ops.config.models.UserType;
import com.hubformath.mathhubservice.ops.config.controllers.UserTypeController;

@Component
public class UserTypeModelAssembler implements RepresentationModelAssembler<UserType, EntityModel<UserType>> {
    @Override
    public EntityModel<UserType> toModel(UserType userType) {

    return EntityModel.of(userType, //
        linkTo(methodOn(UserTypeController.class).one(userType.getId())).withSelfRel(),
        linkTo(methodOn(UserTypeController.class).all()).withRel("userTypes"));
  }
}