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

import com.hubformath.mathhubservice.assemblers.ops.cashbook.ExpenseModelAssembler;
import com.hubformath.mathhubservice.models.ops.cashbook.Expense;
import com.hubformath.mathhubservice.repositories.ops.cashbook.ExpenseRepository;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;


@RestController
@RequestMapping(path="/api/v1/ops")
public class ExpenseController {
    @Autowired
    private final ExpenseRepository repository;
    private final ExpenseModelAssembler assembler;

    public ExpenseController(ExpenseRepository repository, ExpenseModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/expenses")
    public CollectionModel<EntityModel<Expense>> all() {
        List<EntityModel<Expense>> expenses = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(expenses, linkTo(methodOn(ExpenseController.class).all()).withSelfRel());
    }

    @PostMapping("/expenses")
    public ResponseEntity<EntityModel<Expense>> newExpense(@RequestBody Expense newExpense) {
        EntityModel<Expense> entityModel = assembler.toModel(repository.save(newExpense));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/expenses/{id}")
    public EntityModel<Expense> one(@PathVariable Long id) {
        Expense expense = repository.findById(id) //
                .orElseThrow(() -> new ItemNotFoundException(id, "expense"));

        return assembler.toModel(expense);
    }

    @PutMapping("/expenses/{id}")
    public ResponseEntity<EntityModel<Expense>> replaceExpense(@RequestBody Expense newExpense,
            @PathVariable Long id) {
        Expense updatedExpense = repository.findById(id) //
                .map(expense -> {
                    expense.setExpenseType(newExpense.getExpenseType());
                    expense.setPaymentMethod(newExpense.getPaymentMethod());
                    expense.setNarration(newExpense.getNarration());
                    expense.setStatus(newExpense.getStatus());
                    expense.setAmount(newExpense.getAmount());
                    expense.setCreatedBy(newExpense.getCreatedBy());
                    expense.setApprovedBy(newExpense.getApprovedBy());
                    return repository.save(expense);
                }) //
                .orElseThrow(() -> new ItemNotFoundException(id, "expense"));

        EntityModel<Expense> entityModel = assembler.toModel(updatedExpense);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
    
}
