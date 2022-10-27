package com.hubformath.mathhubservice.assemblers.ops.cashbook;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.ops.cashbook.ExpenseController;
import com.hubformath.mathhubservice.models.ops.cashbook.Expense;

@Component
public class ExpenseModelAssembler implements RepresentationModelAssembler<Expense, EntityModel<Expense>> {
    @Override
    public EntityModel<Expense> toModel(Expense expense) {

    return EntityModel.of(expense, //
        linkTo(methodOn(ExpenseController.class).one(expense.getId())).withSelfRel(),
        linkTo(methodOn(ExpenseController.class).all()).withRel("expenses"));
    }
    
}
