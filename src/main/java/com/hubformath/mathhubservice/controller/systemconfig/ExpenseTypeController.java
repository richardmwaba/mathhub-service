package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.ExpenseType;
import com.hubformath.mathhubservice.service.systemconfig.ExpenseTypeService;
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
@RequestMapping(path = "/api/v1/systemconfig/ops")
@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER')")
public class ExpenseTypeController {

    private final ExpenseTypeService expenseTypeService;

    @Autowired
    public ExpenseTypeController(final ExpenseTypeService expenseTypeService) {
        this.expenseTypeService = expenseTypeService;
    }

    @GetMapping("/expenseTypes")
    public ResponseEntity<CollectionModel<EntityModel<ExpenseType>>> getAllExpenseTypes() {
        CollectionModel<EntityModel<ExpenseType>> expenseTypes = toCollectionModel(expenseTypeService.getAllExpenseTypes());

        return ResponseEntity.ok().body(expenseTypes);
    }

    @PostMapping("/expenseTypes")
    public ResponseEntity<EntityModel<ExpenseType>> newExpenseType(@RequestBody final ExpenseType expenseTypeRequest) {
        ExpenseType newExpenseType = expenseTypeService.createExpenseType(expenseTypeRequest);
        EntityModel<ExpenseType> expenseType = toModel(newExpenseType);

        return ResponseEntity.created(expenseType.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(expenseType);
    }

    @GetMapping("/expenseTypes/{expenseTypeId}")
    public ResponseEntity<EntityModel<ExpenseType>> getExpenseTypeById(@PathVariable final String expenseTypeId) {
        try {
            EntityModel<ExpenseType> expenseType = toModel(expenseTypeService.getExpenseTypeById(expenseTypeId));

            return ResponseEntity.ok().body(expenseType);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/expenseTypes/{expenseTypeId}")
    public ResponseEntity<EntityModel<ExpenseType>> replaceExpenseType(@RequestBody final ExpenseType expenseTypeRequest,
                                                                       @PathVariable final String expenseTypeId) {
        try {
            EntityModel<ExpenseType> updatedExpenseType = toModel(expenseTypeService.updateExpenseType(expenseTypeId,
                                                                                                       expenseTypeRequest));
            return ResponseEntity.ok().body(updatedExpenseType);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/expenseTypes/{expenseTypeId}")
    public ResponseEntity<String> deleteExpenseType(@PathVariable final String expenseTypeId) {
        try {
            expenseTypeService.deleteExpenseType(expenseTypeId);
            return ResponseEntity.ok().body("Expense type deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public EntityModel<ExpenseType> toModel(final ExpenseType assessmentType) {
        return EntityModel.of(assessmentType,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseTypeController.class)
                                                                        .getExpenseTypeById(assessmentType.getExpenseTypeId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseTypeController.class)
                                                                        .getAllExpenseTypes()).withRel("expenseTypes"));
    }

    private CollectionModel<EntityModel<ExpenseType>> toCollectionModel(final Iterable<? extends ExpenseType> expenseTypesIterable) {
        List<EntityModel<ExpenseType>> expenseTypes = StreamSupport.stream(expenseTypesIterable.spliterator(), false)
                                                                   .map(this::toModel)
                                                                   .toList();

        return CollectionModel.of(expenseTypes,
                                  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseTypeController.class)
                                                                            .getAllExpenseTypes())
                                                   .withSelfRel());
    }
}
