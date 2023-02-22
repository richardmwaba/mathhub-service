package com.hubformath.mathhubservice.controller.ops.cashbook;

import java.util.List;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.hubformath.mathhubservice.dto.ops.cashbook.CashTransactionDto;
import com.hubformath.mathhubservice.model.ops.cashbook.CashTransaction;
import com.hubformath.mathhubservice.service.ops.cashbook.CashTransactionService;


@RestController
@RequestMapping(path="/api/v1/ops")
public class CashTransactionController {

    private final ModelMapper modelMapper;

    private final CashTransactionService transactionService;

    @Autowired
    public CashTransactionController(final ModelMapper modelMapper, final CashTransactionService transactionService) {
        this.modelMapper = modelMapper;
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public ResponseEntity<CollectionModel<EntityModel<CashTransactionDto>>> getAllTransactions() {
        List<CashTransactionDto> transactions = transactionService.getAllTransactions().stream()
                .map(transaction -> modelMapper.map(transaction, CashTransactionDto.class))
                .toList();

        CollectionModel<EntityModel<CashTransactionDto>> transactionCollectionModel = toCollectionModel(transactions);

        return ResponseEntity.ok().body(transactionCollectionModel);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<EntityModel<CashTransactionDto>> getTransactionById(@PathVariable final Long id) {
        CashTransaction transaction = transactionService.getTransactionById(id);

        EntityModel<CashTransactionDto> transactionEntityModel = toModel(modelMapper.map(transaction, CashTransactionDto.class));

        return ResponseEntity.ok().body(transactionEntityModel);
    }

    @PatchMapping("/transactions/{id}")
    public ResponseEntity<EntityModel<CashTransactionDto>> replaceTransaction(
            @RequestBody final CashTransactionDto transactionDto,
            @PathVariable final Long id) {
        CashTransaction transactionRequest = modelMapper.map(transactionDto, CashTransaction.class);
        CashTransaction updatedTransaction = transactionService.updateTransaction(id, transactionRequest);

        EntityModel<CashTransactionDto> transactionEntityModel = toModel(modelMapper.map(updatedTransaction, CashTransactionDto.class));

        return ResponseEntity.ok().body(transactionEntityModel);

    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable final Long id) {
        transactionService.deleteTransaction(id);

        return ResponseEntity.ok().body("Transaction deleted successfully");
    }

    private EntityModel<CashTransactionDto> toModel(final CashTransactionDto transaction) {
        return EntityModel.of(transaction, //
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CashTransactionController.class).getTransactionById(transaction.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CashTransactionController.class).getAllTransactions()).withRel("transactions"));
    }

    private CollectionModel<EntityModel<CashTransactionDto>> toCollectionModel(final Iterable<? extends CashTransactionDto> transactions) {
        List<EntityModel<CashTransactionDto>> transactionList = StreamSupport.stream(transactions.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(transactionList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CashTransactionController.class)
                        .getAllTransactions())
                .withSelfRel());
    }
}
