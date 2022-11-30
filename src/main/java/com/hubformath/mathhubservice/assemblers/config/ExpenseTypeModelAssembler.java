package com.hubformath.mathhubservice.assemblers.config;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.config.ExpenseTypeController;
import com.hubformath.mathhubservice.dtos.config.ExpenseTypeDto;

@Component
public class ExpenseTypeModelAssembler implements RepresentationModelAssembler<ExpenseTypeDto, EntityModel<ExpenseTypeDto>> {
    @Override
    public EntityModel<ExpenseTypeDto> toModel(ExpenseTypeDto assessmentType) {

        return EntityModel.of(assessmentType,
            linkTo(methodOn(ExpenseTypeController.class).getExpenseTypeById(assessmentType.getId())).withSelfRel(),
            linkTo(methodOn(ExpenseTypeController.class).getAllExpenseTypes()).withRel("expenseTypes"));
    }

    @Override
    public CollectionModel<EntityModel<ExpenseTypeDto>> toCollectionModel(
        Iterable<? extends ExpenseTypeDto> assessmentTypes) {
        List<EntityModel<ExpenseTypeDto>> assessmentTypeList = StreamSupport.stream(assessmentTypes.spliterator(), false)
            .map(this::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(assessmentTypeList, linkTo(methodOn(ExpenseTypeController.class)
              .getAllExpenseTypes())
              .withSelfRel());
    }
}
