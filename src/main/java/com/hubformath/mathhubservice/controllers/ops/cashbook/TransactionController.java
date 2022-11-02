package com.hubformath.mathhubservice.controllers.ops.cashbook;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hubformath.mathhubservice.assemblers.ops.cashbook.TransactionModelAssembler;
import com.hubformath.mathhubservice.models.ops.cashbook.Transaction;
import com.hubformath.mathhubservice.repositories.ops.cashbook.TransactionRepository;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;


@RestController
@RequestMapping(path="/v1/api/ops/transactions")
public class TransactionController {
    @Autowired
    private final TransactionRepository repository;
    private final TransactionModelAssembler assembler;

    public TransactionController(TransactionRepository repository, TransactionModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/transactions")
    public CollectionModel<EntityModel<Transaction>> all() {
        List<EntityModel<Transaction>> transactions = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(transactions, linkTo(methodOn(TransactionController.class).all()).withSelfRel());
    }

    @PostMapping("/transactions")
    public ResponseEntity<EntityModel<Transaction>> newTransaction(@RequestBody Transaction newTransaction) {
        EntityModel<Transaction> entityModel = assembler.toModel(repository.save(newTransaction));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/transactions/{id}")
    public EntityModel<Transaction> one(@PathVariable Long id) {
        Transaction transaction = repository.findById(id) //
                .orElseThrow(() -> new ItemNotFoundException(id, "transaction"));

        return assembler.toModel(transaction);
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<EntityModel<Transaction>> replaceTransaction(@RequestBody Transaction newTransaction,
            @PathVariable Long id) {
        Transaction updatedTransaction = repository.findById(id) //
                .map(transaction -> {
                    transaction.setTransactionType(newTransaction.getTransactionType());
                    transaction.setPaymentMethod(newTransaction.getPaymentMethod());
                    transaction.setTransactionType(newTransaction.getTransactionType());
                    transaction.setNarration(newTransaction.getNarration());
                    transaction.setAmount(newTransaction.getAmount());
                    transaction.setTransactionDate(newTransaction.getTransactionDate());
                    transaction.setTransactedBy(newTransaction.getTransactedBy());
                    return repository.save(transaction);
                }) //
                .orElseThrow(() -> new ItemNotFoundException(id, "transaction"));

        EntityModel<Transaction> entityModel = assembler.toModel(updatedTransaction);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
