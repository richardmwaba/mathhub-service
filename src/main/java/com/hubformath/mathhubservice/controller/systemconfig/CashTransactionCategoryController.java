package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.systemconfig.CashTransactionCategoryDto;
import com.hubformath.mathhubservice.model.systemconfig.CashTransactionCategory;
import com.hubformath.mathhubservice.service.systemconfig.CashTransactionCategoryService;
import org.modelmapper.ModelMapper;
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
import java.util.UUID;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/v1/systemconfig/ops")
@PreAuthorize("hasRole('ADMIN') or hasRole('CASHIER')")
public class CashTransactionCategoryController {

    private final ModelMapper modelMapper;

    private final CashTransactionCategoryService cashTransactionCategoryService;

    @Autowired
    public CashTransactionCategoryController(final ModelMapperConfig modelMapperConfig,
                                             final CashTransactionCategoryService cashTransactionCategoryService) {
        this.modelMapper = modelMapperConfig.createModelMapper();
        this.cashTransactionCategoryService = cashTransactionCategoryService;
    }

    @GetMapping("/cashTransactionCategories")
    public ResponseEntity<CollectionModel<EntityModel<CashTransactionCategoryDto>>> getAllCashTransactionCategories() {
        List<CashTransactionCategoryDto> cashTransactionCategories = cashTransactionCategoryService.getAllCashTransactionCategories()
                                                                                                   .stream()
                                                                                                   .map(cashTransactionCategory -> modelMapper.map(
                                                                                                           cashTransactionCategory,
                                                                                                           CashTransactionCategoryDto.class))
                                                                                                   .toList();
        CollectionModel<EntityModel<CashTransactionCategoryDto>> cashTransactionCategoryCollectionModel = toCollectionModel(
                cashTransactionCategories);

        return ResponseEntity.ok().body(cashTransactionCategoryCollectionModel);
    }

    @PostMapping("/cashTransactionCategories")
    public ResponseEntity<EntityModel<CashTransactionCategoryDto>> newCashTransactionCategory(
            @RequestBody final CashTransactionCategoryDto cashTransactionCategoryDto) {
        CashTransactionCategory cashTransactionCategoryRequest = modelMapper.map(cashTransactionCategoryDto,
                                                                                 CashTransactionCategory.class);
        CashTransactionCategory newCashTransactionCategory = cashTransactionCategoryService.createCashTransactionCategory(
                cashTransactionCategoryRequest);
        EntityModel<CashTransactionCategoryDto> cashTransactionCategoryEntityModel = toModel(modelMapper.map(
                newCashTransactionCategory,
                CashTransactionCategoryDto.class));

        return ResponseEntity.created(cashTransactionCategoryEntityModel.getRequiredLink(IanaLinkRelations.SELF)
                                                                        .toUri())
                             .body(cashTransactionCategoryEntityModel);
    }

    @GetMapping("/cashTransactionCategories/{cashTransactionCategoryId}")
    public ResponseEntity<EntityModel<CashTransactionCategoryDto>> getCashTransactionCategoryById(@PathVariable final UUID cashTransactionCategoryId) {
        try {
            CashTransactionCategory cashTransactionCategory = cashTransactionCategoryService.getCashTransactionCategoryById(
                    cashTransactionCategoryId);
            EntityModel<CashTransactionCategoryDto> cashTransactionCategoryEntityModel = toModel(modelMapper.map(
                    cashTransactionCategory,
                    CashTransactionCategoryDto.class));
            return ResponseEntity.ok().body(cashTransactionCategoryEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/cashTransactionCategories/{cashTransactionCategoryId}")
    public ResponseEntity<EntityModel<CashTransactionCategoryDto>> replaceCashTransactionCategory(@RequestBody final CashTransactionCategoryDto cashTransactionCategoryDto,
                                                                                                  @PathVariable final UUID cashTransactionCategoryId) {
        try {
            CashTransactionCategory cashTransactionCategoryRequest = modelMapper.map(cashTransactionCategoryDto,
                                                                                     CashTransactionCategory.class);
            CashTransactionCategory updatedCashTransactionCategory = cashTransactionCategoryService.updateCashTransactionCategory(
                    cashTransactionCategoryId,
                    cashTransactionCategoryRequest);
            EntityModel<CashTransactionCategoryDto> cashTransactionCategoryEntityModel = toModel(modelMapper.map(
                    updatedCashTransactionCategory,
                    CashTransactionCategoryDto.class));
            return ResponseEntity.ok().body(cashTransactionCategoryEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/cashTransactionCategories/{cashTransactionCategoryId}")
    public ResponseEntity<String> deleteCashTransactionCategory(@PathVariable final UUID cashTransactionCategoryId) {
        try {
            cashTransactionCategoryService.deleteCashTransactionCategory(cashTransactionCategoryId);
            return ResponseEntity.ok().body("Cash Transaction Category deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<CashTransactionCategoryDto> toModel(final CashTransactionCategoryDto cashTransactionCategory) {
        return EntityModel.of(cashTransactionCategory,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CashTransactionCategoryController.class)
                                                                        .getCashTransactionCategoryById(
                                                                                cashTransactionCategory.getCashTransactionCategoryId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CashTransactionCategoryController.class)
                                                                        .getAllCashTransactionCategories())
                                               .withRel("cashTransactionCategories"));
    }

    private CollectionModel<EntityModel<CashTransactionCategoryDto>> toCollectionModel(
            final Iterable<? extends CashTransactionCategoryDto> cashTransactionCategories) {
        List<EntityModel<CashTransactionCategoryDto>> cashTransactionCategoryList = StreamSupport.stream(
                                                                                                         cashTransactionCategories.spliterator(),
                                                                                                         false)
                                                                                                 .map(this::toModel)
                                                                                                 .toList();

        return CollectionModel.of(cashTransactionCategoryList,
                                  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CashTransactionCategoryController.class)
                                                                            .getAllCashTransactionCategories())
                                                   .withSelfRel());
    }
}