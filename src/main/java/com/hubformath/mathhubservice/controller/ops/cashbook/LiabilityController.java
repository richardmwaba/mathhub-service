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

import com.hubformath.mathhubservice.assembler.ops.cashbook.LiabilityModelAssembler;
import com.hubformath.mathhubservice.dto.ops.cashbook.LiabilityDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Liability;
import com.hubformath.mathhubservice.service.ops.cashbook.ILiabilityService;

@RestController
@RequestMapping(path = "/api/v1/ops")
public class LiabilityController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ILiabilityService liabilityService;

    @Autowired
    private LiabilityModelAssembler liabilityModelAssembler;

    public LiabilityController() {
        super();
    }

    @GetMapping("/liabilities")
    public ResponseEntity<CollectionModel<EntityModel<LiabilityDto>>> getAllLiabilities() {
        List<LiabilityDto> liabilities = liabilityService.getAllLiabilities().stream()
                .map(liability -> modelMapper.map(liability, LiabilityDto.class))
                .toList();

        CollectionModel<EntityModel<LiabilityDto>> liabilityCollectionModel = liabilityModelAssembler
                .toCollectionModel(liabilities);

        return ResponseEntity.ok().body(liabilityCollectionModel);
    }

    @PostMapping("/liabilities")
    public ResponseEntity<EntityModel<LiabilityDto>> newLiability(
            @RequestBody LiabilityDto liabilityDto) {
        Liability liabilityRequest = modelMapper.map(liabilityDto, Liability.class);
        Liability newLiability = liabilityService.createLiability(liabilityRequest);

        EntityModel<LiabilityDto> liabilityEntityModel = liabilityModelAssembler
                .toModel(modelMapper.map(newLiability, LiabilityDto.class));

        return ResponseEntity.created(liabilityEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(liabilityEntityModel);
    }

    @GetMapping("/liabilities/{id}")
    public ResponseEntity<EntityModel<LiabilityDto>> getLiabilityById(@PathVariable Long id) {
        Liability liability = liabilityService.getLiabilityById(id);

        EntityModel<LiabilityDto> liabilityEntityModel = liabilityModelAssembler
                .toModel(modelMapper.map(liability, LiabilityDto.class));

        return ResponseEntity.ok().body(liabilityEntityModel);
    }

    @PutMapping("/liabilities/{id}")
    public ResponseEntity<EntityModel<LiabilityDto>> replaceLiability(
            @RequestBody LiabilityDto liabilityDto,
            @PathVariable Long id) {
        Liability liabilityRequest = modelMapper.map(liabilityDto, Liability.class);
        Liability updatedLiability = liabilityService.updateLiability(id, liabilityRequest);

        EntityModel<LiabilityDto> liabilityEntityModel = liabilityModelAssembler
                .toModel(modelMapper.map(updatedLiability, LiabilityDto.class));

        return ResponseEntity.ok().body(liabilityEntityModel);

    }

    @DeleteMapping("/liabilities/{id}")
    public ResponseEntity<String> deleteLiability(@PathVariable Long id) {
        liabilityService.deleteLiability(id);

        return ResponseEntity.ok().body("Liability deleted sucessfully");
    }
}

