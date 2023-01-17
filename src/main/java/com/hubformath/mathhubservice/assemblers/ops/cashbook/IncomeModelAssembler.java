package com.hubformath.mathhubservice.assemblers.ops.cashbook;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.ops.cashbook.IncomeController;
import com.hubformath.mathhubservice.dtos.ops.cashbook.IncomeDto;

@Component
public class IncomeModelAssembler implements RepresentationModelAssembler<IncomeDto, EntityModel<IncomeDto>> {
    @Override
    public EntityModel<IncomeDto> toModel(IncomeDto income) {
        return EntityModel.of(income,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IncomeController.class).getIncomeById(income.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IncomeController.class).getAllIncomes()).withRel("incomes"));
    }

    @Override
    public CollectionModel<EntityModel<IncomeDto>> toCollectionModel(
            Iterable<? extends IncomeDto> incomes) {
        List<EntityModel<IncomeDto>> incomeList = StreamSupport.stream(incomes.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(incomeList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IncomeController.class)
                .getAllIncomes())
                .withSelfRel());
    }
}


