package com.hubformath.mathhubservice.assembler.ops.cashbook;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controller.ops.cashbook.TransactionController;
import com.hubformath.mathhubservice.dto.ops.cashbook.TransactionDto;

@Component
public class TransactionModelAssembler implements RepresentationModelAssembler<TransactionDto, EntityModel<TransactionDto>> {
    @Override
    public EntityModel<TransactionDto> toModel(TransactionDto transaction) {
        return EntityModel.of(transaction, //
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TransactionController.class).getTransactionById(transaction.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TransactionController.class).getAllTransactions()).withRel("transactions"));
    }

    @Override
    public CollectionModel<EntityModel<TransactionDto>> toCollectionModel(
            Iterable<? extends TransactionDto> transactions) {
        List<EntityModel<TransactionDto>> transactionList = StreamSupport.stream(transactions.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(transactionList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TransactionController.class)
                .getAllTransactions())
                .withSelfRel());
    }
}
