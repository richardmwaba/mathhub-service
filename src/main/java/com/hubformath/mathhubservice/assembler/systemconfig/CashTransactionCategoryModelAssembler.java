package com.hubformath.mathhubservice.assembler.systemconfig;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controller.systemconfig.CashTransactionCategoryController;
import com.hubformath.mathhubservice.dto.systemconfig.CashTransactionCategoryDto;

@Component
public class CashTransactionCategoryModelAssembler implements RepresentationModelAssembler<CashTransactionCategoryDto, EntityModel<CashTransactionCategoryDto>>  {
    @Override
    public EntityModel<CashTransactionCategoryDto> toModel(CashTransactionCategoryDto cashTransactionCategory) {
        return EntityModel.of(cashTransactionCategory,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CashTransactionCategoryController.class).getCashTransactionCategoryById(cashTransactionCategory.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CashTransactionCategoryController.class).getAllCashTransactionCategories()).withRel("cashTransactionCategories"));
    }

    @Override
    public CollectionModel<EntityModel<CashTransactionCategoryDto>> toCollectionModel(
            Iterable<? extends CashTransactionCategoryDto> cashTransactionCategories) {
        List<EntityModel<CashTransactionCategoryDto>> cashTransactionCategoryList = StreamSupport.stream(cashTransactionCategories.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(cashTransactionCategoryList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CashTransactionCategoryController.class)
                .getAllCashTransactionCategories())
                .withSelfRel());
    }
}