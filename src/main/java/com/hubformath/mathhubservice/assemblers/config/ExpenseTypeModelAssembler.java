package com.hubformath.mathhubservice.assemblers.config;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.config.ExpenseTypeController;
import com.hubformath.mathhubservice.models.config.ExpenseType;

@Component
public class ExpenseTypeModelAssembler implements RepresentationModelAssembler<ExpenseType, EntityModel<ExpenseType>> {
    @Override
    public EntityModel<ExpenseType> toModel(ExpenseType expenseType) {

    return EntityModel.of(expenseType, //
        linkTo(methodOn(ExpenseTypeController.class).one(expenseType.getId())).withSelfRel(),
        linkTo(methodOn(ExpenseTypeController.class).all()).withRel("expenseTypes"));
  }
}
