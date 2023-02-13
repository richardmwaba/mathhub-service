package com.hubformath.mathhubservice.assembler.systemconfig;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controller.systemconfig.UserTypeController;
import com.hubformath.mathhubservice.model.systemconfig.UserType;

@Component
public class UserTypeModelAssembler implements RepresentationModelAssembler<UserType, EntityModel<UserType>> {
  @Override
  public EntityModel<UserType> toModel(UserType userType) {

    return EntityModel.of(userType, //
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserTypeController.class).one(userType.getId())).withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserTypeController.class).all()).withRel("userTypes"));
  }
}