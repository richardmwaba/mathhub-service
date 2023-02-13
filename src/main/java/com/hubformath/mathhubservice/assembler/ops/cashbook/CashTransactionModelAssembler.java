package com.hubformath.mathhubservice.assembler.ops.cashbook;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controller.ops.cashbook.CashTransactionController;
import com.hubformath.mathhubservice.dto.ops.cashbook.CashTransactionDto;

@Component
public class CashTransactionModelAssembler implements RepresentationModelAssembler<CashTransactionDto, EntityModel<CashTransactionDto>> {
    @Override
    public EntityModel<CashTransactionDto> toModel(CashTransactionDto transaction) {
        return EntityModel.of(transaction, //
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CashTransactionController.class).getTransactionById(transaction.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CashTransactionController.class).getAllTransactions()).withRel("transactions"));
    }

    @Override
    public CollectionModel<EntityModel<CashTransactionDto>> toCollectionModel(
            Iterable<? extends CashTransactionDto> transactions) {
        List<EntityModel<CashTransactionDto>> transactionList = StreamSupport.stream(transactions.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(transactionList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CashTransactionController.class)
                .getAllTransactions())
                .withSelfRel());
    }
}
