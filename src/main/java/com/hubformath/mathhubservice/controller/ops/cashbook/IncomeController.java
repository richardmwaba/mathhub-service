package com.hubformath.mathhubservice.controller.ops.cashbook;

import java.util.List;


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

import com.hubformath.mathhubservice.assembler.ops.cashbook.IncomeModelAssembler;
import com.hubformath.mathhubservice.dto.ops.cashbook.IncomeDto;
import com.hubformath.mathhubservice.dto.ops.cashbook.IncomeRequestDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Income;
import com.hubformath.mathhubservice.service.ops.cashbook.IIncomeService;

@RestController
@RequestMapping(path = "/api/v1/ops")
public class IncomeController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IIncomeService incomeService;

    @Autowired
    private IncomeModelAssembler incomeModelAssembler;

    public IncomeController() {
        super();
    }

    @GetMapping("/incomes")
    public ResponseEntity<CollectionModel<EntityModel<IncomeDto>>> getAllIncomes() {
        List<IncomeDto> incomes = incomeService.getAllIncomes().stream()
                .map(income -> modelMapper.map(income, IncomeDto.class))
                .toList();

        CollectionModel<EntityModel<IncomeDto>> incomeCollectionModel = incomeModelAssembler
                .toCollectionModel(incomes);

        return ResponseEntity.ok().body(incomeCollectionModel);
    }

    @PostMapping("/incomes")
    public ResponseEntity<EntityModel<IncomeDto>> newIncome(
            @RequestBody IncomeRequestDto incomeRequestDto) {
        Income newIncome = incomeService.createIncome(incomeRequestDto);

        EntityModel<IncomeDto> incomeEntityModel = incomeModelAssembler
                .toModel(modelMapper.map(newIncome, IncomeDto.class));

        return ResponseEntity.created(incomeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(incomeEntityModel);
    }

    @GetMapping("/incomes/{id}")
    public ResponseEntity<EntityModel<IncomeDto>> getIncomeById(@PathVariable Long id) {
        Income income = incomeService.getIncomeById(id);

        EntityModel<IncomeDto> incomeEntityModel = incomeModelAssembler
                .toModel(modelMapper.map(income, IncomeDto.class));

        return ResponseEntity.ok().body(incomeEntityModel);
    }

    @PutMapping("/incomes/{id}")
    public ResponseEntity<EntityModel<IncomeDto>> replaceIncome(
            @RequestBody IncomeDto incomeDto,
            @PathVariable Long id) {
        Income incomeRequest = modelMapper.map(incomeDto, Income.class);
        Income updatedIncome = incomeService.updateIncome(id, incomeRequest);

        EntityModel<IncomeDto> incomeEntityModel = incomeModelAssembler
                .toModel(modelMapper.map(updatedIncome, IncomeDto.class));

        return ResponseEntity.ok().body(incomeEntityModel);

    }

    @DeleteMapping("/incomes/{id}")
    public ResponseEntity<String> deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);

        return ResponseEntity.ok().body("Income deleted successfully");
    }
}

