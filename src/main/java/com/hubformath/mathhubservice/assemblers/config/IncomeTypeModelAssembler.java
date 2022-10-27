package com.hubformath.mathhubservice.assemblers.config;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.config.IncomeTypeController;
import com.hubformath.mathhubservice.models.config.IncomeType;

@Component
public class IncomeTypeModelAssembler implements RepresentationModelAssembler<IncomeType, EntityModel<IncomeType>> {
    @Override
    public EntityModel<IncomeType> toModel(IncomeType incomeType) {

    return EntityModel.of(incomeType, //
        linkTo(methodOn(IncomeTypeController.class).one(incomeType.getId())).withSelfRel(),
        linkTo(methodOn(IncomeTypeController.class).all()).withRel("incomeTypes"));
  }
}
