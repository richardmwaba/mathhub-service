package com.hubformath.mathhubservice.assemblers.ops.cashbook;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.ops.cashbook.TransactionController;
import com.hubformath.mathhubservice.models.ops.cashbook.Transaction;

@Component
public class TransactionModelAssembler implements RepresentationModelAssembler<Transaction, EntityModel<Transaction>> {
    @Override
    public EntityModel<Transaction> toModel(Transaction assetType) {

    return EntityModel.of(assetType, //
        linkTo(methodOn(TransactionController.class).one(assetType.getId())).withSelfRel(),
        linkTo(methodOn(TransactionController.class).all()).withRel("assetTypes"));
    }
}
