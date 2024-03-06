package com.hubformath.mathhubservice.controller.ops.cashbook;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.ops.cashbook.ExpenseDto;
import com.hubformath.mathhubservice.dto.ops.cashbook.ExpenseRequestDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Expense;
import com.hubformath.mathhubservice.service.ops.cashbook.ExpenseService;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper;

    @Autowired
    public ExpenseController(final ExpenseService expenseService, final ModelMapperConfig modelMapperConfig) {
        this.expenseService = expenseService;
        this.modelMapper = modelMapperConfig.createModelMapper();
    }

    @GetMapping("/expenses")
    public ResponseEntity<CollectionModel<EntityModel<ExpenseDto>>> getAllExpenses() {
        List<ExpenseDto> expenses = expenseService.getAllExpenses().stream()
                                                  .map(expense -> modelMapper.map(expense, ExpenseDto.class))
                                                  .toList();

        CollectionModel<EntityModel<ExpenseDto>> expenseCollectionModel = toCollectionModel(expenses);

        return ResponseEntity.ok().body(expenseCollectionModel);
    }

    @PostMapping("/expenses")
    public ResponseEntity<EntityModel<ExpenseDto>> newExpense(@RequestBody final ExpenseRequestDto expenseRequestDto) {
        Expense newExpense = expenseService.createExpense(expenseRequestDto);

        EntityModel<ExpenseDto> expenseEntityModel = toModel(modelMapper.map(newExpense, ExpenseDto.class));

        return ResponseEntity.created(expenseEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(expenseEntityModel);
    }

    @GetMapping("/expenses/{expenseId}")
    public ResponseEntity<EntityModel<ExpenseDto>> getExpenseById(@PathVariable final String expenseId) {
        try {
            Expense expense = expenseService.getExpenseById(expenseId);
            EntityModel<ExpenseDto> expenseEntityModel = toModel(modelMapper.map(expense, ExpenseDto.class));
            return ResponseEntity.ok().body(expenseEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/expenses/{expenseId}")
    public ResponseEntity<EntityModel<ExpenseDto>> replaceExpense(@RequestBody final ExpenseDto expenseDto,
                                                                  @PathVariable final String expenseId) {
        try {
            Expense expenseRequest = modelMapper.map(expenseDto, Expense.class);
            Expense updatedExpense = expenseService.updateExpense(expenseId, expenseRequest);
            EntityModel<ExpenseDto> expenseEntityModel = toModel(modelMapper.map(updatedExpense, ExpenseDto.class));
            return ResponseEntity.ok().body(expenseEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/expenses/{expenseId}")
    public ResponseEntity<String> deleteExpense(@PathVariable final String expenseId) {
        try {
            expenseService.deleteExpense(expenseId);
            return ResponseEntity.ok().body("Expense deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<ExpenseDto> toModel(final ExpenseDto expense) {
        return EntityModel.of(expense,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseController.class)
                                                                        .getExpenseById(expense.getExpenseId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseController.class)
                                                                        .getAllExpenses()).withRel("expenses"));
    }

    private CollectionModel<EntityModel<ExpenseDto>> toCollectionModel(final Iterable<? extends ExpenseDto> expenses) {
        List<EntityModel<ExpenseDto>> expenseList = StreamSupport.stream(expenses.spliterator(), false)
                                                                 .map(this::toModel)
                                                                 .toList();

        return CollectionModel.of(expenseList,
                                  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseController.class)
                                                                            .getAllExpenses())
                                                   .withSelfRel());
    }
}
