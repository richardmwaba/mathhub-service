package com.hubformath.mathhubservice.assembler.systemconfig;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controller.systemconfig.ExpenseTypeController;
import com.hubformath.mathhubservice.dto.systemconfig.ExpenseTypeDto;

@Component
public class ExpenseTypeModelAssembler implements RepresentationModelAssembler<ExpenseTypeDto, EntityModel<ExpenseTypeDto>> {
    @Override
    public EntityModel<ExpenseTypeDto> toModel(ExpenseTypeDto assessmentType) {
        return EntityModel.of(assessmentType,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseTypeController.class).getExpenseTypeById(assessmentType.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseTypeController.class).getAllExpenseTypes()).withRel("expenseTypes"));
    }

    @Override
    public CollectionModel<EntityModel<ExpenseTypeDto>> toCollectionModel(
            Iterable<? extends ExpenseTypeDto> assessmentTypes) {
        List<EntityModel<ExpenseTypeDto>> assessmentTypeList = StreamSupport
                .stream(assessmentTypes.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(assessmentTypeList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseTypeController.class)
                .getAllExpenseTypes())
                .withSelfRel());
    }
}
