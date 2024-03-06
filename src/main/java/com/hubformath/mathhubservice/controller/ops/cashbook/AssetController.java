package com.hubformath.mathhubservice.controller.ops.cashbook;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.ops.cashbook.AssetDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Asset;
import com.hubformath.mathhubservice.service.ops.cashbook.AssetService;
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
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/v1/ops")
@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER')")
public class AssetController {

    private final ModelMapper modelMapper;

    private final AssetService assetService;

    @Autowired
    public AssetController(final ModelMapperConfig modelMapperConfig, final AssetService assetService) {
        this.modelMapper = modelMapperConfig.createModelMapper();
        this.assetService = assetService;
    }

    @GetMapping("/assets")
    public ResponseEntity<CollectionModel<EntityModel<AssetDto>>> getAllAssets() {
        List<AssetDto> assets = assetService.getAllAssets().stream()
                                            .map(asset -> modelMapper.map(asset, AssetDto.class))
                                            .toList();

        CollectionModel<EntityModel<AssetDto>> assetCollectionModel = toCollectionModel(assets);

        return ResponseEntity.ok().body(assetCollectionModel);
    }

    @PostMapping("/assets")
    public ResponseEntity<EntityModel<AssetDto>> newAsset(@RequestBody final AssetDto assetDto) {
        Asset assetRequest = modelMapper.map(assetDto, Asset.class);
        Asset newAsset = assetService.createAsset(assetRequest);

        EntityModel<AssetDto> assetEntityModel = toModel(modelMapper.map(newAsset, AssetDto.class));

        return ResponseEntity.created(assetEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(assetEntityModel);
    }

    @GetMapping("/assets/{assetId}")
    public ResponseEntity<EntityModel<AssetDto>> getAssetById(@PathVariable final String assetId) {
        try {
            Asset asset = assetService.getAssetById(assetId);
            EntityModel<AssetDto> assetEntityModel = toModel(modelMapper.map(asset, AssetDto.class));
            return ResponseEntity.ok().body(assetEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/assets/{assetId}")
    public ResponseEntity<EntityModel<AssetDto>> replaceAsset(@RequestBody final AssetDto assetDto,
                                                              @PathVariable final String assetId) {
        try {
            Asset assetRequest = modelMapper.map(assetDto, Asset.class);
            Asset updatedAsset = assetService.updateAsset(assetId, assetRequest);
            EntityModel<AssetDto> assetEntityModel = toModel(modelMapper.map(updatedAsset, AssetDto.class));
            return ResponseEntity.ok().body(assetEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/assets/{assetId}")
    public ResponseEntity<String> deleteAsset(@PathVariable String assetId) {
        try {
            assetService.deleteAsset(assetId);
            return ResponseEntity.ok().body("Asset deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<AssetDto> toModel(final AssetDto asset) {
        return EntityModel.of(asset,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssetController.class)
                                                                        .getAssetById(asset.getAssetId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssetController.class).getAllAssets())
                                               .withRel("assets"));
    }

    private CollectionModel<EntityModel<AssetDto>> toCollectionModel(final Iterable<? extends AssetDto> assets) {
        List<EntityModel<AssetDto>> assetList = StreamSupport.stream(assets.spliterator(), false)
                                                             .map(this::toModel)
                                                             .toList();

        return CollectionModel.of(assetList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssetController.class)
                                                                                       .getAllAssets())
                                                              .withSelfRel());
    }
}
