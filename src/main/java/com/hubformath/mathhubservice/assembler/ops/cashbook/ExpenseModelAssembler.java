package com.hubformath.mathhubservice.assembler.ops.cashbook;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controller.ops.cashbook.ExpenseController;
import com.hubformath.mathhubservice.dto.ops.cashbook.ExpenseDto;

@Component
public class ExpenseModelAssembler implements RepresentationModelAssembler<ExpenseDto, EntityModel<ExpenseDto>> {
    @Override
    public EntityModel<ExpenseDto> toModel(ExpenseDto expense) {
        return EntityModel.of(expense,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseController.class).getExpenseById(expense.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseController.class).getAllExpenses()).withRel("expenses"));
    }

    @Override
    public CollectionModel<EntityModel<ExpenseDto>> toCollectionModel(
            Iterable<? extends ExpenseDto> expenses) {
        List<EntityModel<ExpenseDto>> expenseList = StreamSupport.stream(expenses.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(expenseList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseController.class)
                .getAllExpenses())
                .withSelfRel());
    }
    
}
