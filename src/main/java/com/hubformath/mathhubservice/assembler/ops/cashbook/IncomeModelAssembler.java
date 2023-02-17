package com.hubformath.mathhubservice.assembler.ops.cashbook;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controller.ops.cashbook.IncomeController;
import com.hubformath.mathhubservice.dto.ops.cashbook.IncomeDto;

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
                .toList();

        return CollectionModel.of(incomeList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IncomeController.class)
                .getAllIncomes())
                .withSelfRel());
    }
}


