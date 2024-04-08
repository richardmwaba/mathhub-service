package com.hubformath.mathhubservice.controller.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.CashTransaction;
import com.hubformath.mathhubservice.model.ops.cashbook.CashTransactionRequest;
import com.hubformath.mathhubservice.service.ops.cashbook.CashTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;


@RestController
@RequestMapping(path = "/api/v1/ops")
@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER')")
public class CashTransactionController {

    private final CashTransactionService transactionService;

    @Autowired
    public CashTransactionController(CashTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public ResponseEntity<CollectionModel<EntityModel<CashTransaction>>> getAllTransactions() {
        List<CashTransaction> transactions = transactionService.getAllTransactions();
        CollectionModel<EntityModel<CashTransaction>> transactionCollectionModel = toCollectionModel(transactions);

        return ResponseEntity.ok().body(transactionCollectionModel);
    }

    @GetMapping("/transactions/{cashTransactionId}")
    public ResponseEntity<EntityModel<CashTransaction>> getTransactionById(@PathVariable String cashTransactionId) {
        try {
            EntityModel<CashTransaction> transaction = toModel(transactionService.getTransactionById(cashTransactionId));
            return ResponseEntity.ok().body(transaction);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/transactions/{cashTransactionId}")
    public ResponseEntity<EntityModel<CashTransaction>> updateTransaction(@RequestBody CashTransactionRequest transactionRequest,
                                                                          @PathVariable String cashTransactionId) {
        try {
            EntityModel<CashTransaction> updatedTransaction =
                    toModel(transactionService.updateTransaction(cashTransactionId, transactionRequest));
            return ResponseEntity.ok().body(updatedTransaction);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/transactions/{cashTransactionId}")
    public ResponseEntity<String> deleteTransaction(@PathVariable String cashTransactionId) {
        try {
            transactionService.deleteTransaction(cashTransactionId);
            return ResponseEntity.ok().body("Transaction deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<CashTransaction> toModel(CashTransaction transaction) {
        return EntityModel.of(transaction,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CashTransactionController.class)
                                                                        .getTransactionById(transaction.getCashTransactionId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CashTransactionController.class)
                                                                        .getAllTransactions()).withRel("transactions"));
    }

    private CollectionModel<EntityModel<CashTransaction>> toCollectionModel(Iterable<? extends CashTransaction> transactionsIterable) {
        List<EntityModel<CashTransaction>> transactions = StreamSupport.stream(transactionsIterable.spliterator(), false)
                                                                             .map(this::toModel)
                                                                             .toList();

        return CollectionModel.of(transactions,
                                  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CashTransactionController.class)
                                                                            .getAllTransactions())
                                                   .withSelfRel());
    }
}
