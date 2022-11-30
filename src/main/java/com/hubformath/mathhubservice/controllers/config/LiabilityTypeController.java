package com.hubformath.mathhubservice.controllers.config;

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

import com.hubformath.mathhubservice.assemblers.config.LiabilityTypeModelAssembler;
import com.hubformath.mathhubservice.dtos.config.LiabilityTypeDto;
import com.hubformath.mathhubservice.models.config.LiabilityType;
import com.hubformath.mathhubservice.services.config.ILiabilityTypeService;

@RestController
@RequestMapping(path="/api/v1/ops")
public class LiabilityTypeController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ILiabilityTypeService liabilityTypeService;

    @Autowired
    private LiabilityTypeModelAssembler liabilityTypeModelAssembler;

    public LiabilityTypeController() {
        super();
    }

    @GetMapping("/liabilityTypes")
    public ResponseEntity<CollectionModel<EntityModel<LiabilityTypeDto>>> getAllLiabilityTypes() {
        List<LiabilityTypeDto> liabilityTypes = liabilityTypeService.getAllLiabilityTypes().stream()
                .map(liabilityType -> modelMapper.map(liabilityType, LiabilityTypeDto.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<LiabilityTypeDto>> liabilityTypeCollectionModel = liabilityTypeModelAssembler
                .toCollectionModel(liabilityTypes);

        return ResponseEntity.ok().body(liabilityTypeCollectionModel);
    }

    @PostMapping("/liabilityTypes")
    public ResponseEntity<EntityModel<LiabilityTypeDto>> newLiabilityType(
            @RequestBody LiabilityTypeDto liabilityTypeDto) {
        LiabilityType liabilityTypeRequest = modelMapper.map(liabilityTypeDto, LiabilityType.class);
        LiabilityType newLiabilityType = liabilityTypeService.createLiabilityType(liabilityTypeRequest);

        EntityModel<LiabilityTypeDto> liabilityTypeEntityModel = liabilityTypeModelAssembler
                .toModel(modelMapper.map(newLiabilityType, LiabilityTypeDto.class));

        return ResponseEntity.created(liabilityTypeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(liabilityTypeEntityModel);
    }

    @GetMapping("/liabilityTypes/{id}")
    public ResponseEntity<EntityModel<LiabilityTypeDto>> getLiabilityTypeById(@PathVariable Long id) {
        LiabilityType liabilityType = liabilityTypeService.getLiabilityTypeById(id);

        EntityModel<LiabilityTypeDto> liabilityTypeEntityModel = liabilityTypeModelAssembler
                .toModel(modelMapper.map(liabilityType, LiabilityTypeDto.class));

        return ResponseEntity.ok().body(liabilityTypeEntityModel);
    }

    @PutMapping("/liabilityTypes/{id}")
    public ResponseEntity<EntityModel<LiabilityTypeDto>> replaceLiabilityType(
            @RequestBody LiabilityTypeDto liabilityTypeDto,
            @PathVariable Long id) {
        LiabilityType liabilityTypeRequest = modelMapper.map(liabilityTypeDto, LiabilityType.class);
        LiabilityType updatedLiabilityType = liabilityTypeService.updateLiabilityType(id, liabilityTypeRequest);

        EntityModel<LiabilityTypeDto> liabilityTypeEntityModel = liabilityTypeModelAssembler
                .toModel(modelMapper.map(updatedLiabilityType, LiabilityTypeDto.class));

        return ResponseEntity.ok().body(liabilityTypeEntityModel);

    }

    @DeleteMapping("/liabilityTypes/{id}")
    public ResponseEntity<String> deleteLiabilityType(@PathVariable Long id) {
        liabilityTypeService.deleteLiabilityType(id);

        return ResponseEntity.ok().body("Liability type deleted succefully");
    }
}
