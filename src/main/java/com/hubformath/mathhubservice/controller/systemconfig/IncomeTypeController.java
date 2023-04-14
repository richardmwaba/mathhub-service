package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.systemconfig.IncomeTypeDto;
import com.hubformath.mathhubservice.model.systemconfig.IncomeType;
import com.hubformath.mathhubservice.service.systemconfig.IncomeTypeService;
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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path="/api/v1/systemconfig/ops")
public class IncomeTypeController {

    private final ModelMapper modelMapper;

    private final IncomeTypeService incomeTypeService;

    @Autowired
    public IncomeTypeController(final ModelMapperConfig modelMapperConfig, IncomeTypeService incomeTypeService) {
        this.modelMapper = modelMapperConfig.createModelMapper();
        this.incomeTypeService = incomeTypeService;
    }

    @GetMapping("/incomeTypes")
    public ResponseEntity<CollectionModel<EntityModel<IncomeTypeDto>>> getAllIncomeTypes() {
        List<IncomeTypeDto> incomeTypes = incomeTypeService.getAllIncomeTypes().stream()
                .map(incomeType -> modelMapper.map(incomeType, IncomeTypeDto.class))
                .toList();

        CollectionModel<EntityModel<IncomeTypeDto>> incomeTypeCollectionModel = toCollectionModel(incomeTypes);

        return ResponseEntity.ok().body(incomeTypeCollectionModel);
    }

    @PostMapping("/incomeTypes")
    public ResponseEntity<EntityModel<IncomeTypeDto>> newIncomeType(@RequestBody final IncomeTypeDto incomeTypeDto) {
        IncomeType incomeTypeRequest = modelMapper.map(incomeTypeDto, IncomeType.class);
        IncomeType newIncomeType = incomeTypeService.createIncomeType(incomeTypeRequest);
        EntityModel<IncomeTypeDto> incomeTypeEntityModel = toModel(modelMapper.map(newIncomeType, IncomeTypeDto.class));

        return ResponseEntity.created(incomeTypeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(incomeTypeEntityModel);
    }

    @GetMapping("/incomeTypes/{incomeTypeId}")
    public ResponseEntity<EntityModel<IncomeTypeDto>> getIncomeTypeById(@PathVariable final UUID incomeTypeId) {
        try {
            IncomeType incomeType = incomeTypeService.getIncomeTypeById(incomeTypeId);
            EntityModel<IncomeTypeDto> incomeTypeEntityModel = toModel(modelMapper.map(incomeType, IncomeTypeDto.class));
            return ResponseEntity.ok().body(incomeTypeEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/incomeTypes/{incomeTypeId}")
    public ResponseEntity<EntityModel<IncomeTypeDto>> replaceIncomeType(@RequestBody final IncomeTypeDto incomeTypeDto,
                                                                        @PathVariable final UUID incomeTypeId) {
        try {
            IncomeType incomeTypeRequest = modelMapper.map(incomeTypeDto, IncomeType.class);
            IncomeType updatedIncomeType = incomeTypeService.updateIncomeType(incomeTypeId, incomeTypeRequest);
            EntityModel<IncomeTypeDto> incomeTypeEntityModel = toModel(modelMapper.map(updatedIncomeType, IncomeTypeDto.class));
            return ResponseEntity.ok().body(incomeTypeEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/incomeTypes/{incomeTypeId}")
    public ResponseEntity<String> deleteIncomeType(@PathVariable final UUID incomeTypeId) {
        try {
            incomeTypeService.deleteIncomeType(incomeTypeId);
            return ResponseEntity.ok().body("Income type deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public EntityModel<IncomeTypeDto> toModel(final IncomeTypeDto incomeType) {
        return EntityModel.of(incomeType,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IncomeTypeController.class).getIncomeTypeById(incomeType.getIncomeTypeId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IncomeTypeController.class).getAllIncomeTypes()).withRel("incomeTypes"));
    }

    private CollectionModel<EntityModel<IncomeTypeDto>> toCollectionModel(final Iterable<? extends IncomeTypeDto> incomeTypes) {
        List<EntityModel<IncomeTypeDto>> incomeTypeList = StreamSupport.stream(incomeTypes.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(incomeTypeList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IncomeTypeController.class)
                        .getAllIncomeTypes())
                .withSelfRel());
    }
}
