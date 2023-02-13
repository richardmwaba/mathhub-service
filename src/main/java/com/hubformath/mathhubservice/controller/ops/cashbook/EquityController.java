package com.hubformath.mathhubservice.controller.ops.cashbook;


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

import com.hubformath.mathhubservice.assembler.ops.cashbook.EquityModelAssembler;
import com.hubformath.mathhubservice.dto.ops.cashbook.EquityDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Equity;
import com.hubformath.mathhubservice.service.ops.cashbook.IEquityService;

@RestController
@RequestMapping(path = "/api/v1/ops")
public class EquityController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IEquityService equityService;

    @Autowired
    private EquityModelAssembler equityModelAssembler;

    public EquityController() {
        super();
    }

    @GetMapping("/equity")
    public ResponseEntity<CollectionModel<EntityModel<EquityDto>>> getAllEquity() {
        List<EquityDto> equityList = equityService.getAllEquity().stream()
                .map(equity -> modelMapper.map(equity, EquityDto.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<EquityDto>> equityCollectionModel = equityModelAssembler
                .toCollectionModel(equityList);

        return ResponseEntity.ok().body(equityCollectionModel);
    }

    @PostMapping("/equity")
    public ResponseEntity<EntityModel<EquityDto>> newEquity(
            @RequestBody EquityDto equityDto) {
        Equity equityRequest = modelMapper.map(equityDto, Equity.class);
        Equity newEquity = equityService.createEquity(equityRequest);

        EntityModel<EquityDto> equityEntityModel = equityModelAssembler
                .toModel(modelMapper.map(newEquity, EquityDto.class));

        return ResponseEntity.created(equityEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(equityEntityModel);
    }

    @GetMapping("/equity/{id}")
    public ResponseEntity<EntityModel<EquityDto>> getEquityById(@PathVariable Long id) {
        Equity equity = equityService.getEquityById(id);

        EntityModel<EquityDto> equityEntityModel = equityModelAssembler
                .toModel(modelMapper.map(equity, EquityDto.class));

        return ResponseEntity.ok().body(equityEntityModel);
    }

    @PutMapping("/equity/{id}")
    public ResponseEntity<EntityModel<EquityDto>> replaceEquity(
            @RequestBody EquityDto equityDto,
            @PathVariable Long id) {
        Equity equityRequest = modelMapper.map(equityDto, Equity.class);
        Equity updatedEquity = equityService.updateEquity(id, equityRequest);

        EntityModel<EquityDto> equityEntityModel = equityModelAssembler
                .toModel(modelMapper.map(updatedEquity, EquityDto.class));

        return ResponseEntity.ok().body(equityEntityModel);

    }

    @DeleteMapping("/equity/{id}")
    public ResponseEntity<String> deleteEquity(@PathVariable Long id) {
        equityService.deleteEquity(id);

        return ResponseEntity.ok().body("Equity deleted successfully");
    }
    
}
