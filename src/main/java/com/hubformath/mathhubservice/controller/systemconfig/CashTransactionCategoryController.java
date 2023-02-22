package com.hubformath.mathhubservice.controller.systemconfig;

import java.util.List;
import java.util.stream.StreamSupport;

import com.hubformath.mathhubservice.service.systemconfig.CashTransactionCategoryService;
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

import com.hubformath.mathhubservice.dto.systemconfig.CashTransactionCategoryDto;
import com.hubformath.mathhubservice.model.systemconfig.CashTransactionCategory;

@RestController
@RequestMapping(path="/api/v1/systemconfig/ops")
public class CashTransactionCategoryController {

    private final ModelMapper modelMapper;

    private final CashTransactionCategoryService cashTransactionCategoryService;

    @Autowired
    public CashTransactionCategoryController(final ModelMapper modelMapper, final CashTransactionCategoryService cashTransactionCategoryService) {
        this.modelMapper = modelMapper;
        this.cashTransactionCategoryService = cashTransactionCategoryService;
    }

    @GetMapping("/cashTransactionCategories")
    public ResponseEntity<CollectionModel<EntityModel<CashTransactionCategoryDto>>> getAllCashTransactionCategories() {
        List<CashTransactionCategoryDto> cashTransactionCategories = cashTransactionCategoryService.getAllCashTransactionCategories().stream()
                .map(cashTransactionCategory -> modelMapper.map(cashTransactionCategory, CashTransactionCategoryDto.class))
                .toList();
        CollectionModel<EntityModel<CashTransactionCategoryDto>> cashTransactionCategoryCollectionModel = toCollectionModel(cashTransactionCategories);

        return ResponseEntity.ok().body(cashTransactionCategoryCollectionModel);
    }

    @PostMapping("/cashTransactionCategories")
    public ResponseEntity<EntityModel<CashTransactionCategoryDto>> newCashTransactionCategory(
            @RequestBody final CashTransactionCategoryDto cashTransactionCategoryDto) {
        CashTransactionCategory cashTransactionCategoryRequest = modelMapper.map(cashTransactionCategoryDto, CashTransactionCategory.class);
        CashTransactionCategory newCashTransactionCategory = cashTransactionCategoryService.createCashTransactionCategory(cashTransactionCategoryRequest);
        EntityModel<CashTransactionCategoryDto> cashTransactionCategoryEntityModel = toModel(modelMapper.map(newCashTransactionCategory, CashTransactionCategoryDto.class));

        return ResponseEntity.created(cashTransactionCategoryEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(cashTransactionCategoryEntityModel);
    }

    @GetMapping("/cashTransactionCategories/{id}")
    public ResponseEntity<EntityModel<CashTransactionCategoryDto>> getCashTransactionCategoryById(@PathVariable final Long id) {
        CashTransactionCategory cashTransactionCategory = cashTransactionCategoryService.getCashTransactionCategoryById(id);
        EntityModel<CashTransactionCategoryDto> cashTransactionCategoryEntityModel = toModel(modelMapper.map(cashTransactionCategory, CashTransactionCategoryDto.class));

        return ResponseEntity.ok().body(cashTransactionCategoryEntityModel);
    }

    @PutMapping("/cashTransactionCategories/{id}")
    public ResponseEntity<EntityModel<CashTransactionCategoryDto>> replaceCashTransactionCategory(
            @RequestBody final CashTransactionCategoryDto cashTransactionCategoryDto,
            @PathVariable final Long id) {
        CashTransactionCategory cashTransactionCategoryRequest = modelMapper.map(cashTransactionCategoryDto, CashTransactionCategory.class);
        CashTransactionCategory updatedCashTransactionCategory = cashTransactionCategoryService.updateCashTransactionCategory(id, cashTransactionCategoryRequest);

        EntityModel<CashTransactionCategoryDto> cashTransactionCategoryEntityModel = toModel(modelMapper.map(updatedCashTransactionCategory, CashTransactionCategoryDto.class));

        return ResponseEntity.ok().body(cashTransactionCategoryEntityModel);
    }

    @DeleteMapping("/cashTransactionCategories/{id}")
    public ResponseEntity<String> deleteCashTransactionCategory(@PathVariable final Long id) {
        cashTransactionCategoryService.deleteCashTransactionCategory(id);
        return ResponseEntity.ok().body("Cash Transaction Category deleted successfully");
    }

    private EntityModel<CashTransactionCategoryDto> toModel(final CashTransactionCategoryDto cashTransactionCategory) {
        return EntityModel.of(cashTransactionCategory,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CashTransactionCategoryController.class).getCashTransactionCategoryById(cashTransactionCategory.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CashTransactionCategoryController.class).getAllCashTransactionCategories()).withRel("cashTransactionCategories"));
    }

    private CollectionModel<EntityModel<CashTransactionCategoryDto>> toCollectionModel(
            final Iterable<? extends CashTransactionCategoryDto> cashTransactionCategories) {
        List<EntityModel<CashTransactionCategoryDto>> cashTransactionCategoryList = StreamSupport.stream(cashTransactionCategories.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(cashTransactionCategoryList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CashTransactionCategoryController.class)
                        .getAllCashTransactionCategories())
                .withSelfRel());
    }
}