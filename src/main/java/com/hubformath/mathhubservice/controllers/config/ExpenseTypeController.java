package com.hubformath.mathhubservice.controllers.config;

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

import com.hubformath.mathhubservice.assemblers.config.ExpenseTypeModelAssembler;
import com.hubformath.mathhubservice.dtos.config.ExpenseTypeDto;
import com.hubformath.mathhubservice.models.config.ExpenseType;
import com.hubformath.mathhubservice.services.config.IExpenseTypeService;

@RestController
@RequestMapping(path="/api/v1/ops")
public class ExpenseTypeController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IExpenseTypeService expenseTypeService;

    @Autowired
    private ExpenseTypeModelAssembler expenseTypeModelAssembler;

    public ExpenseTypeController() {
        super();
    }

    @GetMapping("/expenseTypes")
    public ResponseEntity<CollectionModel<EntityModel<ExpenseTypeDto>>> getAllExpenseTypes() {
        List<ExpenseTypeDto> expenseTypes = expenseTypeService.getAllExpenseTypes().stream()
                .map(expenseType -> modelMapper.map(expenseType, ExpenseTypeDto.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ExpenseTypeDto>> expenseTypeCollectionModel = expenseTypeModelAssembler
                .toCollectionModel(expenseTypes);

        return ResponseEntity.ok().body(expenseTypeCollectionModel);
    }

    @PostMapping("/expenseTypes")
    public ResponseEntity<EntityModel<ExpenseTypeDto>> newExpenseType(
            @RequestBody ExpenseTypeDto expenseTypeDto) {
        ExpenseType expenseTypeRequest = modelMapper.map(expenseTypeDto, ExpenseType.class);
        ExpenseType newExpenseType = expenseTypeService.createExpenseType(expenseTypeRequest);

        EntityModel<ExpenseTypeDto> expenseTypeEntityModel = expenseTypeModelAssembler
                .toModel(modelMapper.map(newExpenseType, ExpenseTypeDto.class));

        return ResponseEntity.created(expenseTypeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(expenseTypeEntityModel);
    }

    @GetMapping("/expenseTypes/{id}")
    public ResponseEntity<EntityModel<ExpenseTypeDto>> getExpenseTypeById(@PathVariable Long id) {
        ExpenseType expenseType = expenseTypeService.getExpenseTypeById(id);

        EntityModel<ExpenseTypeDto> expenseTypeEntityModel = expenseTypeModelAssembler
                .toModel(modelMapper.map(expenseType, ExpenseTypeDto.class));

        return ResponseEntity.ok().body(expenseTypeEntityModel);
    }

    @PutMapping("/expenseTypes/{id}")
    public ResponseEntity<EntityModel<ExpenseTypeDto>> replaceExpenseType(
            @RequestBody ExpenseTypeDto expenseTypeDto,
            @PathVariable Long id) {
        ExpenseType expenseTypeRequest = modelMapper.map(expenseTypeDto, ExpenseType.class);
        ExpenseType updatedExpenseType = expenseTypeService.updateExpenseType(id, expenseTypeRequest);

        EntityModel<ExpenseTypeDto> expenseTypeEntityModel = expenseTypeModelAssembler
                .toModel(modelMapper.map(updatedExpenseType, ExpenseTypeDto.class));

        return ResponseEntity.ok().body(expenseTypeEntityModel);

    }

    @DeleteMapping("/expenseTypes/{id}")
    public ResponseEntity<String> deleteExpenseType(@PathVariable Long id) {
        expenseTypeService.deleteExpenseType(id);

        return ResponseEntity.ok().body("Expense type deleted successfully");
    }
}
