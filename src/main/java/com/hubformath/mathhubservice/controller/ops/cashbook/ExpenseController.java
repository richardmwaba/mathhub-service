package com.hubformath.mathhubservice.controller.ops.cashbook;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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

import com.hubformath.mathhubservice.assembler.ops.cashbook.ExpenseModelAssembler;
import com.hubformath.mathhubservice.dto.ops.cashbook.ExpenseDto;
import com.hubformath.mathhubservice.dto.ops.cashbook.ExpenseRequestDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Expense;
import com.hubformath.mathhubservice.service.ops.cashbook.IExpenseService;


@RestController
@RequestMapping(path="/api/v1/ops")
public class ExpenseController {

    @Autowired
    private IExpenseService expenseService;
    
    @Autowired
    private ExpenseModelAssembler expenseModelAssembler;

    @Autowired
    private ModelMapper modelMapper;

    public ExpenseController() {
        super();
    }

    @GetMapping("/expenses")
    public ResponseEntity<CollectionModel<EntityModel<ExpenseDto>>> getAllExpenses() {
        List<ExpenseDto> expenses = expenseService.getAllExpenses().stream()
                .map(expense -> modelMapper.map(expense, ExpenseDto.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ExpenseDto>> expenseCollectionModel = expenseModelAssembler
                .toCollectionModel(expenses);

        return ResponseEntity.ok().body(expenseCollectionModel);
    }

    @PostMapping("/expenses")
    public ResponseEntity<EntityModel<ExpenseDto>> newExpense(
            @RequestBody ExpenseRequestDto expenseRequestDto) {
        Expense newExpense = expenseService.createExpense(expenseRequestDto);

        EntityModel<ExpenseDto> expenseEntityModel = expenseModelAssembler
                .toModel(modelMapper.map(newExpense, ExpenseDto.class));

        return ResponseEntity.created(expenseEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(expenseEntityModel);
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<EntityModel<ExpenseDto>> getExpenseById(@PathVariable Long id) {
        Expense expense = expenseService.getExpenseById(id);

        EntityModel<ExpenseDto> expenseEntityModel = expenseModelAssembler
                .toModel(modelMapper.map(expense, ExpenseDto.class));

        return ResponseEntity.ok().body(expenseEntityModel);
    }

    @PutMapping("/expenses/{id}")
    public ResponseEntity<EntityModel<ExpenseDto>> replaceExpense(
            @RequestBody ExpenseDto expenseDto,
            @PathVariable Long id) {
        Expense expenseRequest = modelMapper.map(expenseDto, Expense.class);
        Expense updatedExpense = expenseService.updateExpense(id, expenseRequest);

        EntityModel<ExpenseDto> expenseEntityModel = expenseModelAssembler
                .toModel(modelMapper.map(updatedExpense, ExpenseDto.class));

        return ResponseEntity.ok().body(expenseEntityModel);

    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);

        return ResponseEntity.ok().body("Expense deleted successfully");
    } 
}
