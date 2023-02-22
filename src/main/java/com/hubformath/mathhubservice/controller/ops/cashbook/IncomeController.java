package com.hubformath.mathhubservice.controller.ops.cashbook;

import java.util.List;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hubformath.mathhubservice.dto.ops.cashbook.IncomeDto;
import com.hubformath.mathhubservice.dto.ops.cashbook.IncomeRequestDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Income;
import com.hubformath.mathhubservice.service.ops.cashbook.IncomeService;

@RestController
@RequestMapping(path = "/api/v1/ops")
public class IncomeController {

    private final ModelMapper modelMapper;

    private final IncomeService incomeService;

    @Autowired
    public IncomeController(final ModelMapper modelMapper, final IncomeService incomeService) {
        this.modelMapper = modelMapper;
        this.incomeService = incomeService;
    }

    @GetMapping("/incomes")
    public ResponseEntity<CollectionModel<EntityModel<IncomeDto>>> getAllIncomes() {
        List<IncomeDto> incomes = incomeService.getAllIncomes().stream()
                .map(income -> modelMapper.map(income, IncomeDto.class))
                .toList();

        CollectionModel<EntityModel<IncomeDto>> incomeCollectionModel = toCollectionModel(incomes);

        return ResponseEntity.ok().body(incomeCollectionModel);
    }

    @PostMapping("/incomes")
    public ResponseEntity<EntityModel<IncomeDto>> newIncome( @RequestBody final IncomeRequestDto incomeRequestDto) {
        Income newIncome = incomeService.createIncome(incomeRequestDto);

        EntityModel<IncomeDto> incomeEntityModel = toModel(modelMapper.map(newIncome, IncomeDto.class));

        return ResponseEntity.created(incomeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(incomeEntityModel);
    }

    @GetMapping("/incomes/{id}")
    public ResponseEntity<EntityModel<IncomeDto>> getIncomeById(@PathVariable final Long id) {
        Income income = incomeService.getIncomeById(id);

        EntityModel<IncomeDto> incomeEntityModel = toModel(modelMapper.map(income, IncomeDto.class));

        return ResponseEntity.ok().body(incomeEntityModel);
    }

    @PutMapping("/incomes/{id}")
    public ResponseEntity<EntityModel<IncomeDto>> replaceIncome(
            @RequestBody final IncomeDto incomeDto,
            @PathVariable final Long id) {
        Income incomeRequest = modelMapper.map(incomeDto, Income.class);
        Income updatedIncome = incomeService.updateIncome(id, incomeRequest);

        EntityModel<IncomeDto> incomeEntityModel = toModel(modelMapper.map(updatedIncome, IncomeDto.class));

        return ResponseEntity.ok().body(incomeEntityModel);

    }

    @DeleteMapping("/incomes/{id}")
    public ResponseEntity<String> deleteIncome(@PathVariable final Long id) {
        incomeService.deleteIncome(id);

        return ResponseEntity.ok().body("Income deleted successfully");
    }

    private EntityModel<IncomeDto> toModel(final IncomeDto income) {
        return EntityModel.of(income,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IncomeController.class).getIncomeById(income.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IncomeController.class).getAllIncomes()).withRel("incomes"));
    }

    private CollectionModel<EntityModel<IncomeDto>> toCollectionModel(final Iterable<? extends IncomeDto> incomes) {
        List<EntityModel<IncomeDto>> incomeList = StreamSupport.stream(incomes.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(incomeList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IncomeController.class)
                        .getAllIncomes())
                .withSelfRel());
    }
}

