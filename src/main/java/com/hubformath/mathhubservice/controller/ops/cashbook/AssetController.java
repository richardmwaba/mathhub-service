package com.hubformath.mathhubservice.controller.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.Asset;
import com.hubformath.mathhubservice.model.ops.cashbook.AssetRequest;
import com.hubformath.mathhubservice.service.ops.cashbook.AssetService;
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

    private final AssetService assetService;

    @Autowired
    public AssetController(final AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping("/assets")
    public ResponseEntity<CollectionModel<EntityModel<Asset>>> getAllAssets() {
        CollectionModel<EntityModel<Asset>> assets = toCollectionModel(assetService.getAllAssets());
        return ResponseEntity.ok().body(assets);
    }

    @PostMapping("/assets")
    public ResponseEntity<EntityModel<Asset>> createAsset(@RequestBody final AssetRequest assetRequest) {
        EntityModel<Asset> assetEntityModel = toModel(assetService.createAsset(assetRequest));
        return ResponseEntity.created(assetEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(assetEntityModel);
    }

    @GetMapping("/assets/{assetId}")
    public ResponseEntity<EntityModel<Asset>> getAssetById(@PathVariable final String assetId) {
        try {
            EntityModel<Asset> assetEntityModel = toModel(assetService.getAssetById(assetId));
            return ResponseEntity.ok().body(assetEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/assets/{assetId}")
    public ResponseEntity<EntityModel<Asset>> updateAsset(@RequestBody final AssetRequest assetRequest,
                                                          @PathVariable final String assetId) {
        try {
            EntityModel<Asset> assetEntityModel = toModel(assetService.updateAsset(assetId, assetRequest));
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

    private EntityModel<Asset> toModel(final Asset asset) {
        return EntityModel.of(asset,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssetController.class)
                                                                        .getAssetById(asset.getAssetId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssetController.class).getAllAssets())
                                               .withRel("assets"));
    }

    private CollectionModel<EntityModel<Asset>> toCollectionModel(final Iterable<? extends Asset> assetsIterable) {
        List<EntityModel<Asset>> assets = StreamSupport.stream(assetsIterable.spliterator(), false)
                                                       .map(this::toModel)
                                                       .toList();

        return CollectionModel.of(assets, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssetController.class)
                                                                                    .getAllAssets())
                                                           .withSelfRel());
    }
}
