package com.hubformath.mathhubservice.controller.systemconfig;

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

import com.hubformath.mathhubservice.assembler.systemconfig.CashTransactionCategoryModelAssembler;
import com.hubformath.mathhubservice.dto.systemconfig.CashTransactionCategoryDto;
import com.hubformath.mathhubservice.model.systemconfig.CashTransactionCategory;
import com.hubformath.mathhubservice.service.systemconfig.ICashTransactionCategoryService;

@RestController
@RequestMapping(path="/api/v1/systemconfig/ops")
public class CashTransactionCategoryController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ICashTransactionCategoryService cashTransactionCategoryService;

    @Autowired
    private CashTransactionCategoryModelAssembler cashTransactionCategoryModelAssembler;

    public CashTransactionCategoryController() {
        super();
    }

    @GetMapping("/cashTransactionCategories")
    public ResponseEntity<CollectionModel<EntityModel<CashTransactionCategoryDto>>> getAllCashTransactionCategories() {
        List<CashTransactionCategoryDto> cashTransactionCategories = cashTransactionCategoryService.getAllCashTransactionCategories().stream()
                .map(cashTransactionCategory -> modelMapper.map(cashTransactionCategory, CashTransactionCategoryDto.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<CashTransactionCategoryDto>> cashTransactionCategoryCollectionModel = cashTransactionCategoryModelAssembler
                .toCollectionModel(cashTransactionCategories);

        return ResponseEntity.ok().body(cashTransactionCategoryCollectionModel);
    }

    @PostMapping("/cashTransactionCategories")
    public ResponseEntity<EntityModel<CashTransactionCategoryDto>> newCashTransactionCategory(
            @RequestBody CashTransactionCategoryDto cashTransactionCategoryDto) {
        CashTransactionCategory cashTransactionCategoryRequest = modelMapper.map(cashTransactionCategoryDto, CashTransactionCategory.class);
        CashTransactionCategory newCashTransactionCategory = cashTransactionCategoryService.createCashTransactionCategory(cashTransactionCategoryRequest);

        EntityModel<CashTransactionCategoryDto> cashTransactionCategoryEntityModel = cashTransactionCategoryModelAssembler
                .toModel(modelMapper.map(newCashTransactionCategory, CashTransactionCategoryDto.class));

        return ResponseEntity.created(cashTransactionCategoryEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(cashTransactionCategoryEntityModel);
    }

    @GetMapping("/cashTransactionCategories/{id}")
    public ResponseEntity<EntityModel<CashTransactionCategoryDto>> getCashTransactionCategoryById(@PathVariable Long id) {
        CashTransactionCategory cashTransactionCategory = cashTransactionCategoryService.getCashTransactionCategoryById(id);

        EntityModel<CashTransactionCategoryDto> cashTransactionCategoryEntityModel = cashTransactionCategoryModelAssembler
                .toModel(modelMapper.map(cashTransactionCategory, CashTransactionCategoryDto.class));

        return ResponseEntity.ok().body(cashTransactionCategoryEntityModel);
    }

    @PutMapping("/cashTransactionCategories/{id}")
    public ResponseEntity<EntityModel<CashTransactionCategoryDto>> replaceCashTransactionCategory(
            @RequestBody CashTransactionCategoryDto cashTransactionCategoryDto,
            @PathVariable Long id) {
        CashTransactionCategory cashTransactionCategoryRequest = modelMapper.map(cashTransactionCategoryDto, CashTransactionCategory.class);
        CashTransactionCategory updatedCashTransactionCategory = cashTransactionCategoryService.updateCashTransactionCategory(id, cashTransactionCategoryRequest);

        EntityModel<CashTransactionCategoryDto> cashTransactionCategoryEntityModel = cashTransactionCategoryModelAssembler
                .toModel(modelMapper.map(updatedCashTransactionCategory, CashTransactionCategoryDto.class));

        return ResponseEntity.ok().body(cashTransactionCategoryEntityModel);

    }

    @DeleteMapping("/cashTransactionCategories/{id}")
    public ResponseEntity<String> deleteCashTransactionCategory(@PathVariable Long id) {
        cashTransactionCategoryService.deleteCashTransactionCategory(id);

        return ResponseEntity.ok().body("Cash Transaction Category deleted successfully");
    }
}