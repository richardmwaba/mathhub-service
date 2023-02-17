package com.hubformath.mathhubservice.controller.systemconfig;

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

import com.hubformath.mathhubservice.assembler.systemconfig.IncomeTypeModelAssembler;
import com.hubformath.mathhubservice.dto.systemconfig.IncomeTypeDto;
import com.hubformath.mathhubservice.model.systemconfig.IncomeType;
import com.hubformath.mathhubservice.service.systemconfig.IIncomeTypeService;

@RestController
@RequestMapping(path="/api/v1/ops")
public class IncomeTypeController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IIncomeTypeService incomeTypeService;

    @Autowired
    private IncomeTypeModelAssembler incomeTypeModelAssembler;

    public IncomeTypeController() {
        super();
    }

    @GetMapping("/incomeTypes")
    public ResponseEntity<CollectionModel<EntityModel<IncomeTypeDto>>> getAllIncomeTypes() {
        List<IncomeTypeDto> incomeTypes = incomeTypeService.getAllIncomeTypes().stream()
                .map(incomeType -> modelMapper.map(incomeType, IncomeTypeDto.class))
                .toList();

        CollectionModel<EntityModel<IncomeTypeDto>> incomeTypeCollectionModel = incomeTypeModelAssembler
                .toCollectionModel(incomeTypes);

        return ResponseEntity.ok().body(incomeTypeCollectionModel);
    }

    @PostMapping("/incomeTypes")
    public ResponseEntity<EntityModel<IncomeTypeDto>> newIncomeType(
            @RequestBody IncomeTypeDto incomeTypeDto) {
        IncomeType incomeTypeRequest = modelMapper.map(incomeTypeDto, IncomeType.class);
        IncomeType newIncomeType = incomeTypeService.createIncomeType(incomeTypeRequest);

        EntityModel<IncomeTypeDto> incomeTypeEntityModel = incomeTypeModelAssembler
                .toModel(modelMapper.map(newIncomeType, IncomeTypeDto.class));

        return ResponseEntity.created(incomeTypeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(incomeTypeEntityModel);
    }

    @GetMapping("/incomeTypes/{id}")
    public ResponseEntity<EntityModel<IncomeTypeDto>> getIncomeTypeById(@PathVariable Long id) {
        IncomeType incomeType = incomeTypeService.getIncomeTypeById(id);

        EntityModel<IncomeTypeDto> incomeTypeEntityModel = incomeTypeModelAssembler
                .toModel(modelMapper.map(incomeType, IncomeTypeDto.class));

        return ResponseEntity.ok().body(incomeTypeEntityModel);
    }

    @PutMapping("/incomeTypes/{id}")
    public ResponseEntity<EntityModel<IncomeTypeDto>> replaceIncomeType(
            @RequestBody IncomeTypeDto incomeTypeDto,
            @PathVariable Long id) {
        IncomeType incomeTypeRequest = modelMapper.map(incomeTypeDto, IncomeType.class);
        IncomeType updatedIncomeType = incomeTypeService.updateIncomeType(id, incomeTypeRequest);

        EntityModel<IncomeTypeDto> incomeTypeEntityModel = incomeTypeModelAssembler
                .toModel(modelMapper.map(updatedIncomeType, IncomeTypeDto.class));

        return ResponseEntity.ok().body(incomeTypeEntityModel);

    }

    @DeleteMapping("/incomeTypes/{id}")
    public ResponseEntity<String> deleteIncomeType(@PathVariable Long id) {
        incomeTypeService.deleteIncomeType(id);

        return ResponseEntity.ok().body("Income type deleted successfully");
    }
}
