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

import com.hubformath.mathhubservice.assembler.ops.cashbook.AssetModelAssembler;
import com.hubformath.mathhubservice.dto.ops.cashbook.AssetDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Asset;
import com.hubformath.mathhubservice.service.ops.cashbook.AssetService;

@RestController
@RequestMapping(path = "/api/v1/ops")
public class AssetController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AssetService assetService;

    @Autowired
    private AssetModelAssembler assetModelAssembler;

    public AssetController() {
        super();
    }

    @GetMapping("/assets")
    public ResponseEntity<CollectionModel<EntityModel<AssetDto>>> getAllAssets() {
        List<AssetDto> assets = assetService.getAllAssets().stream()
                .map(asset -> modelMapper.map(asset, AssetDto.class))
                .toList();

        CollectionModel<EntityModel<AssetDto>> assetCollectionModel = assetModelAssembler
                .toCollectionModel(assets);

        return ResponseEntity.ok().body(assetCollectionModel);
    }

    @PostMapping("/assets")
    public ResponseEntity<EntityModel<AssetDto>> newAsset(
            @RequestBody AssetDto assetDto) {
        Asset assetRequest = modelMapper.map(assetDto, Asset.class);
        Asset newAsset = assetService.createAsset(assetRequest);

        EntityModel<AssetDto> assetEntityModel = assetModelAssembler
                .toModel(modelMapper.map(newAsset, AssetDto.class));

        return ResponseEntity.created(assetEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(assetEntityModel);
    }

    @GetMapping("/assets/{id}")
    public ResponseEntity<EntityModel<AssetDto>> getAssetById(@PathVariable Long id) {
        Asset asset = assetService.getAssetById(id);

        EntityModel<AssetDto> assetEntityModel = assetModelAssembler
                .toModel(modelMapper.map(asset, AssetDto.class));

        return ResponseEntity.ok().body(assetEntityModel);
    }

    @PutMapping("/assets/{id}")
    public ResponseEntity<EntityModel<AssetDto>> replaceAsset(
            @RequestBody AssetDto assetDto,
            @PathVariable Long id) {
        Asset assetRequest = modelMapper.map(assetDto, Asset.class);
        Asset updatedAsset = assetService.updateAsset(id, assetRequest);

        EntityModel<AssetDto> assetEntityModel = assetModelAssembler
                .toModel(modelMapper.map(updatedAsset, AssetDto.class));

        return ResponseEntity.ok().body(assetEntityModel);

    }

    @DeleteMapping("/assets/{id}")
    public ResponseEntity<String> deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);

        return ResponseEntity.ok().body("Asset deleted sucessfully");
    }
}
