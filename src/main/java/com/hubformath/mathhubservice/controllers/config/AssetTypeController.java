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

import com.hubformath.mathhubservice.assemblers.config.AssetTypeModelAssembler;
import com.hubformath.mathhubservice.dtos.config.AssetTypeDto;
import com.hubformath.mathhubservice.models.config.AssetType;
import com.hubformath.mathhubservice.services.config.IAssetTypeService;

@RestController
@RequestMapping(path="/api/v1/ops")
public class AssetTypeController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IAssetTypeService assetTypeService;

    @Autowired
    private AssetTypeModelAssembler assetTypeModelAssembler;

    public AssetTypeController() {
        super();
    }

    @GetMapping("/assetTypes")
    public ResponseEntity<CollectionModel<EntityModel<AssetTypeDto>>> getAllAssetTypes() {
        List<AssetTypeDto> assetTypes = assetTypeService.getAllAssetTypes().stream()
                .map(assetType -> modelMapper.map(assetType, AssetTypeDto.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<AssetTypeDto>> assetTypeCollectionModel = assetTypeModelAssembler
                .toCollectionModel(assetTypes);

        return ResponseEntity.ok().body(assetTypeCollectionModel);
    }

    @PostMapping("/assetTypes")
    public ResponseEntity<EntityModel<AssetTypeDto>> newAssetType(
            @RequestBody AssetTypeDto assetTypeDto) {
        AssetType assetTypeRequest = modelMapper.map(assetTypeDto, AssetType.class);
        AssetType newAssetType = assetTypeService.createAssetType(assetTypeRequest);

        EntityModel<AssetTypeDto> assetTypeEntityModel = assetTypeModelAssembler
                .toModel(modelMapper.map(newAssetType, AssetTypeDto.class));

        return ResponseEntity.created(assetTypeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(assetTypeEntityModel);
    }

    @GetMapping("/assetTypes/{id}")
    public ResponseEntity<EntityModel<AssetTypeDto>> getAssetTypeById(@PathVariable Long id) {
        AssetType assetType = assetTypeService.getAssetTypeById(id);

        EntityModel<AssetTypeDto> assetTypeEntityModel = assetTypeModelAssembler
                .toModel(modelMapper.map(assetType, AssetTypeDto.class));

        return ResponseEntity.ok().body(assetTypeEntityModel);
    }

    @PutMapping("/assetTypes/{id}")
    public ResponseEntity<EntityModel<AssetTypeDto>> replaceAssetType(
            @RequestBody AssetTypeDto assetTypeDto,
            @PathVariable Long id) {
        AssetType assetTypeRequest = modelMapper.map(assetTypeDto, AssetType.class);
        AssetType updatedAssetType = assetTypeService.updateAssetType(id, assetTypeRequest);

        EntityModel<AssetTypeDto> assetTypeEntityModel = assetTypeModelAssembler
                .toModel(modelMapper.map(updatedAssetType, AssetTypeDto.class));

        return ResponseEntity.ok().body(assetTypeEntityModel);

    }

    @DeleteMapping("/assetTypes/{id}")
    public ResponseEntity<String> deleteAssetType(@PathVariable Long id) {
        assetTypeService.deleteAssetType(id);

        return ResponseEntity.ok().body("Assessment type deleted succefully");
    }
}
