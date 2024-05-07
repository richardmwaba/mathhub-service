package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.controller.util.CollectionModelUtils;
import com.hubformath.mathhubservice.model.systemconfig.ExpenseType;
import com.hubformath.mathhubservice.service.systemconfig.ExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
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

@RestController
@RequestMapping(path = "/api/v1/systemconfig/ops")
@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER')")
public class ExpenseTypeController {

    private final ExpenseTypeService expenseTypeService;

    @Autowired
    public ExpenseTypeController(ExpenseTypeService expenseTypeService) {
        this.expenseTypeService = expenseTypeService;
    }

    @GetMapping("/expenseTypes")
    public ResponseEntity<CollectionModel<?>> getAllExpenseTypes() {
        List<ExpenseType> expenseTypes = expenseTypeService.getAllExpenseTypes();
        return ResponseEntity.ok().body(toCollectionModel(expenseTypes));
    }

    @PostMapping("/expenseTypes")
    public ResponseEntity<EntityModel<ExpenseType>> newExpenseType(@RequestBody ExpenseType expenseTypeRequest) {
        ExpenseType newExpenseType = expenseTypeService.createExpenseType(expenseTypeRequest);
        EntityModel<ExpenseType> expenseType = toModel(newExpenseType);

        return ResponseEntity.created(expenseType.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(expenseType);
    }

    @GetMapping("/expenseTypes/{expenseTypeId}")
    public ResponseEntity<EntityModel<ExpenseType>> getExpenseTypeById(@PathVariable String expenseTypeId) {
        try {
            EntityModel<ExpenseType> expenseType = toModel(expenseTypeService.getExpenseTypeById(expenseTypeId));

            return ResponseEntity.ok().body(expenseType);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/expenseTypes/{expenseTypeId}")
    public ResponseEntity<EntityModel<ExpenseType>> replaceExpenseType(@RequestBody ExpenseType expenseTypeRequest,
                                                                       @PathVariable String expenseTypeId) {
        try {
            EntityModel<ExpenseType> updatedExpenseType = toModel(expenseTypeService.updateExpenseType(expenseTypeId,
                                                                                                       expenseTypeRequest));
            return ResponseEntity.ok().body(updatedExpenseType);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/expenseTypes/{expenseTypeId}")
    public ResponseEntity<String> deleteExpenseType(@PathVariable String expenseTypeId) {
        try {
            expenseTypeService.deleteExpenseType(expenseTypeId);
            return ResponseEntity.ok().body("Expense type deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public EntityModel<ExpenseType> toModel(ExpenseType assessmentType) {
        return EntityModel.of(assessmentType,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseTypeController.class)
                                                                        .getExpenseTypeById(assessmentType.getExpenseTypeId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseTypeController.class)
                                                                        .getAllExpenseTypes()).withRel("expenseTypes"));
    }

    private CollectionModel<?> toCollectionModel(List<ExpenseType> expenseTypeList) {
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseTypeController.class)
                                                              .getAllExpenseTypes()).withSelfRel();
        List<EntityModel<ExpenseType>> expenseTypes = expenseTypeList.stream()
                                                                   .map(this::toModel)
                                                                   .toList();

        return expenseTypeList.isEmpty()
                ? CollectionModelUtils.getEmptyEmbeddedCollectionModel(ExpenseType.class, link)
                : CollectionModel.of(expenseTypes, link);
    }
}
