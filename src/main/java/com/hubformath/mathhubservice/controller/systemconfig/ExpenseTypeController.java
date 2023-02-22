package com.hubformath.mathhubservice.controller.systemconfig;

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

import com.hubformath.mathhubservice.dto.systemconfig.ExpenseTypeDto;
import com.hubformath.mathhubservice.model.systemconfig.ExpenseType;
import com.hubformath.mathhubservice.service.systemconfig.IExpenseTypeService;

@RestController
@RequestMapping(path="/api/v1/systemconfig/ops")
public class ExpenseTypeController {

    private final ModelMapper modelMapper;

    private final IExpenseTypeService expenseTypeService;

    @Autowired
    public ExpenseTypeController(final ModelMapper modelMapper, final IExpenseTypeService expenseTypeService) {
        this.modelMapper = modelMapper;
        this.expenseTypeService = expenseTypeService;
    }

    @GetMapping("/expenseTypes")
    public ResponseEntity<CollectionModel<EntityModel<ExpenseTypeDto>>> getAllExpenseTypes() {
        List<ExpenseTypeDto> expenseTypes = expenseTypeService.getAllExpenseTypes().stream()
                .map(expenseType -> modelMapper.map(expenseType, ExpenseTypeDto.class))
                .toList();

        CollectionModel<EntityModel<ExpenseTypeDto>> expenseTypeCollectionModel = toCollectionModel(expenseTypes);

        return ResponseEntity.ok().body(expenseTypeCollectionModel);
    }

    @PostMapping("/expenseTypes")
    public ResponseEntity<EntityModel<ExpenseTypeDto>> newExpenseType(@RequestBody final ExpenseTypeDto expenseTypeDto) {
        ExpenseType expenseTypeRequest = modelMapper.map(expenseTypeDto, ExpenseType.class);
        ExpenseType newExpenseType = expenseTypeService.createExpenseType(expenseTypeRequest);
        EntityModel<ExpenseTypeDto> expenseTypeEntityModel = toModel(modelMapper.map(newExpenseType, ExpenseTypeDto.class));

        return ResponseEntity.created(expenseTypeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(expenseTypeEntityModel);
    }

    @GetMapping("/expenseTypes/{id}")
    public ResponseEntity<EntityModel<ExpenseTypeDto>> getExpenseTypeById(@PathVariable final Long id) {
        ExpenseType expenseType = expenseTypeService.getExpenseTypeById(id);
        EntityModel<ExpenseTypeDto> expenseTypeEntityModel = toModel(modelMapper.map(expenseType, ExpenseTypeDto.class));

        return ResponseEntity.ok().body(expenseTypeEntityModel);
    }

    @PutMapping("/expenseTypes/{id}")
    public ResponseEntity<EntityModel<ExpenseTypeDto>> replaceExpenseType(
            @RequestBody final ExpenseTypeDto expenseTypeDto,
            @PathVariable final Long id) {
        ExpenseType expenseTypeRequest = modelMapper.map(expenseTypeDto, ExpenseType.class);
        ExpenseType updatedExpenseType = expenseTypeService.updateExpenseType(id, expenseTypeRequest);
        EntityModel<ExpenseTypeDto> expenseTypeEntityModel = toModel(modelMapper.map(updatedExpenseType, ExpenseTypeDto.class));

        return ResponseEntity.ok().body(expenseTypeEntityModel);

    }

    @DeleteMapping("/expenseTypes/{id}")
    public ResponseEntity<String> deleteExpenseType(@PathVariable final Long id) {
        expenseTypeService.deleteExpenseType(id);
        return ResponseEntity.ok().body("Expense type deleted successfully");
    }

    public EntityModel<ExpenseTypeDto> toModel(final ExpenseTypeDto assessmentType) {
        return EntityModel.of(assessmentType,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseTypeController.class).getExpenseTypeById(assessmentType.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseTypeController.class).getAllExpenseTypes()).withRel("expenseTypes"));
    }

    private CollectionModel<EntityModel<ExpenseTypeDto>> toCollectionModel(final Iterable<? extends ExpenseTypeDto> assessmentTypes) {
        List<EntityModel<ExpenseTypeDto>> assessmentTypeList = StreamSupport
                .stream(assessmentTypes.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(assessmentTypeList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExpenseTypeController.class)
                        .getAllExpenseTypes())
                .withSelfRel());
    }
}
