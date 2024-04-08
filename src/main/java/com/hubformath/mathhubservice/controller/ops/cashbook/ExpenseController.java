package com.hubformath.mathhubservice.controller.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.Expense;
import com.hubformath.mathhubservice.model.ops.cashbook.ExpenseRequest;
import com.hubformath.mathhubservice.service.ops.cashbook.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;


@RestController
@RequestMapping(path = "/api/v1/ops")
@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER')")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expenses")
    public ResponseEntity<CollectionModel<EntityModel<Expense>>> getAllExpenses() {
        CollectionModel<EntityModel<Expense>> expenseCollectionModel = toCollectionModel(expenseService.getAllExpenses());
        return ResponseEntity.ok().body(expenseCollectionModel);
    }

    @PostMapping("/expenses")
    public ResponseEntity<EntityModel<Expense>> newExpense(@RequestBody ExpenseRequest expenseRequest) {
        EntityModel<Expense> newExpense = toModel(expenseService.createExpense(expenseRequest));

        return ResponseEntity.created(newExpense.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(newExpense);
    }

    @GetMapping("/expenses/{expenseId}")
    public ResponseEntity<EntityModel<Expense>> getExpenseById(@PathVariable String expenseId) {
        try {
            EntityModel<Expense> expense = toModel(expenseService.getExpenseById(expenseId));
            return ResponseEntity.ok().body(expense);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/expenses/{expenseId}")
    public ResponseEntity<EntityModel<Expense>> updateExpense(@RequestBody ExpenseRequest expenseRequest,
                                                              @PathVariable String expenseId) {
        try {
            EntityModel<Expense> updatedExpense = toModel(expenseService.updateExpense(expenseId, expenseRequest));
            return ResponseEntity.ok().body(updatedExpense);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/expenses/{expenseId}")
    public ResponseEntity<String> deleteExpense(@PathVariable String expenseId) {
        try {
            expenseService.deleteExpense(expenseId);
            return ResponseEntity.ok().body("Expense deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<Expense> toModel(Expense expense) {
        return EntityModel.of(expense,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseController.class)
                                                                        .getExpenseById(expense.getExpenseId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseController.class)
                                                                        .getAllExpenses()).withRel("expenses"));
    }

    private CollectionModel<EntityModel<Expense>> toCollectionModel(Iterable<? extends Expense> expensesIterable) {
        List<EntityModel<Expense>> expenses = StreamSupport.stream(expensesIterable.spliterator(), false)
                                                           .map(this::toModel)
                                                           .toList();

        return CollectionModel.of(expenses,
                                  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseController.class)
                                                                            .getAllExpenses())
                                                   .withSelfRel());
    }
}
