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

import com.hubformath.mathhubservice.assembler.systemconfig.EquityTypeModelAssembler;
import com.hubformath.mathhubservice.dto.systemconfig.EquityTypeDto;
import com.hubformath.mathhubservice.model.systemconfig.EquityType;
import com.hubformath.mathhubservice.service.systemconfig.IEquityTypeService;

@RestController
@RequestMapping(path="/api/v1/ops")
public class EquityTypeController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IEquityTypeService equityTypeService;

    @Autowired
    private EquityTypeModelAssembler equityTypeModelAssembler;

    public EquityTypeController() {
        super();
    }

    @GetMapping("/equityTypes")
    public ResponseEntity<CollectionModel<EntityModel<EquityTypeDto>>> getAllEquityTypes() {
        List<EquityTypeDto> equityTypes = equityTypeService.getAllEquityTypes().stream()
                .map(equityType -> modelMapper.map(equityType, EquityTypeDto.class))
                .toList();

        CollectionModel<EntityModel<EquityTypeDto>> equityTypeCollectionModel = equityTypeModelAssembler
                .toCollectionModel(equityTypes);

        return ResponseEntity.ok().body(equityTypeCollectionModel);
    }

    @PostMapping("/equityTypes")
    public ResponseEntity<EntityModel<EquityTypeDto>> newEquityType(
            @RequestBody EquityTypeDto equityTypeDto) {
        EquityType equityTypeRequest = modelMapper.map(equityTypeDto, EquityType.class);
        EquityType newEquityType = equityTypeService.createEquityType(equityTypeRequest);

        EntityModel<EquityTypeDto> equityTypeEntityModel = equityTypeModelAssembler
                .toModel(modelMapper.map(newEquityType, EquityTypeDto.class));

        return ResponseEntity.created(equityTypeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(equityTypeEntityModel);
    }

    @GetMapping("/equityTypes/{id}")
    public ResponseEntity<EntityModel<EquityTypeDto>> getEquityTypeById(@PathVariable Long id) {
        EquityType equityType = equityTypeService.getEquityTypeById(id);

        EntityModel<EquityTypeDto> equityTypeEntityModel = equityTypeModelAssembler
                .toModel(modelMapper.map(equityType, EquityTypeDto.class));

        return ResponseEntity.ok().body(equityTypeEntityModel);
    }

    @PutMapping("/equityTypes/{id}")
    public ResponseEntity<EntityModel<EquityTypeDto>> replaceEquityType(
            @RequestBody EquityTypeDto equityTypeDto,
            @PathVariable Long id) {
        EquityType equityTypeRequest = modelMapper.map(equityTypeDto, EquityType.class);
        EquityType updatedEquityType = equityTypeService.updateEquityType(id, equityTypeRequest);

        EntityModel<EquityTypeDto> equityTypeEntityModel = equityTypeModelAssembler
                .toModel(modelMapper.map(updatedEquityType, EquityTypeDto.class));

        return ResponseEntity.ok().body(equityTypeEntityModel);

    }

    @DeleteMapping("/equityTypes/{id}")
    public ResponseEntity<String> deleteEquityType(@PathVariable Long id) {
        equityTypeService.deleteEquityType(id);

        return ResponseEntity.ok().body("Equity type deleted successfully");
    }
}
