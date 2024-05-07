package com.hubformath.mathhubservice.controller.ops.cashbook;

import com.hubformath.mathhubservice.controller.util.CollectionModelUtils;
import com.hubformath.mathhubservice.model.ops.cashbook.Income;
import com.hubformath.mathhubservice.model.ops.cashbook.IncomeRequest;
import com.hubformath.mathhubservice.service.ops.cashbook.IncomeService;
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
@RequestMapping(path = "/api/v1/ops")
@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER')")
public class IncomeController {

    private final IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping("/incomes")
    public ResponseEntity<CollectionModel<?>> getAllIncomes() {
        List<Income> incomes = incomeService.getAllIncomes();
        return ResponseEntity.ok().body(toCollectionModel(incomes));
    }

    @PostMapping("/incomes")
    public ResponseEntity<EntityModel<Income>> newIncome(@RequestBody IncomeRequest incomeRequest) {
        EntityModel<Income> newIncome = toModel(incomeService.createIncome(incomeRequest));
        return ResponseEntity.created(newIncome.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(newIncome);
    }

    @GetMapping("/incomes/{incomeId}")
    public ResponseEntity<EntityModel<Income>> getIncomeById(@PathVariable String incomeId) {
        try {
            EntityModel<Income> income = toModel(incomeService.getIncomeById(incomeId));
            return ResponseEntity.ok().body(income);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/incomes/{incomeId}")
    public ResponseEntity<EntityModel<Income>> replaceIncome(@RequestBody IncomeRequest incomeRequest,
                                                             @PathVariable String incomeId) {
        try {
            EntityModel<Income> incomeEntityModel = toModel(incomeService.updateIncome(incomeId, incomeRequest));
            return ResponseEntity.ok().body(incomeEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/incomes/{incomeId}")
    public ResponseEntity<String> deleteIncome(@PathVariable String incomeId) {
        try {
            incomeService.deleteIncome(incomeId);
            return ResponseEntity.ok().body("Income deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<Income> toModel(Income income) {
        return EntityModel.of(income,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IncomeController.class)
                                                                        .getIncomeById(income.getIncomeId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IncomeController.class)
                                                                        .getAllIncomes()).withRel("incomes"));
    }

    private CollectionModel<?> toCollectionModel(List<Income> incomeList) {
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IncomeController.class)
                                                              .getAllIncomes()).withSelfRel();
        List<EntityModel<Income>> incomes = incomeList.stream()
                                                      .map(this::toModel)
                                                      .toList();

        return incomeList.isEmpty()
                ? CollectionModelUtils.getEmptyEmbeddedCollectionModel(Income.class, link)
                : CollectionModel.of(incomes, link);
    }
}

