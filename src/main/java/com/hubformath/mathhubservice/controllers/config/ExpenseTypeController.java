package com.hubformath.mathhubservice.controllers.config;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
import com.hubformath.mathhubservice.models.config.ExpenseType;
import com.hubformath.mathhubservice.repositories.config.ExpenseTypeRepository;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@RestController
@RequestMapping(path="/api/v1/ops")
public class ExpenseTypeController {
    private final ExpenseTypeRepository repository;
    private final ExpenseTypeModelAssembler assembler;

    public ExpenseTypeController(ExpenseTypeRepository repository, ExpenseTypeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/expenseTypes")
    public CollectionModel<EntityModel<ExpenseType>> all() {
        List<EntityModel<ExpenseType>> expenseTypes = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(expenseTypes, linkTo(methodOn(ExpenseTypeController.class).all()).withSelfRel());
    }

    @PostMapping("/expenseTypes")
    public ResponseEntity<EntityModel<ExpenseType>> newExpenseType(@RequestBody ExpenseType newExpenseType) {
        EntityModel<ExpenseType> entityModel = assembler.toModel(repository.save(newExpenseType));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/expenseTypes/{id}")
    public EntityModel<ExpenseType> one(@PathVariable Long id) {
        ExpenseType expenseType = repository.findById(id) //
                .orElseThrow(() -> new ItemNotFoundException(id, "expenseType"));

        return assembler.toModel(expenseType);
    }

    @PutMapping("/expenseTypes/{id}")
    public ResponseEntity<EntityModel<ExpenseType>> replaceExpenseType(@RequestBody ExpenseType newExpenseType,
            @PathVariable Long id) {
        ExpenseType updatedExpenseType = repository.findById(id) //
                .map(expenseType -> {
                    expenseType.setTypeName(newExpenseType.getTypeName());
                    expenseType.setTypeDescription(newExpenseType.getTypeDescription());
                    return repository.save(expenseType);
                }) //
                .orElseThrow(() -> new ItemNotFoundException(id, "expense type"));

        EntityModel<ExpenseType> entityModel = assembler.toModel(updatedExpenseType);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/expenseTypes/{id}")
    public ResponseEntity<?> deleteExpenseType(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
