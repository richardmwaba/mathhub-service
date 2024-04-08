package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.AssetType;
import com.hubformath.mathhubservice.service.systemconfig.AssetTypeService;
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
@RequestMapping(path = "/api/v1/systemconfig/ops")
@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER')")
public class AssetTypeController {

    private final AssetTypeService assetTypeService;

    @Autowired
    public AssetTypeController(final AssetTypeService assetTypeService) {
        this.assetTypeService = assetTypeService;
    }

    @GetMapping("/assetTypes")
    public ResponseEntity<CollectionModel<EntityModel<AssetType>>> getAllAssetTypes() {
        CollectionModel<EntityModel<AssetType>> assetTypes = toCollectionModel(assetTypeService.getAllAssetTypes());

        return ResponseEntity.ok().body(assetTypes);
    }

    @PostMapping("/assetTypes")
    public ResponseEntity<EntityModel<AssetType>> createAssetType(@RequestBody final AssetType assetTypeRequest) {
        AssetType newAssetType = assetTypeService.createAssetType(assetTypeRequest);
        EntityModel<AssetType> assetTypeEntityModel = toModel(newAssetType);

        return ResponseEntity.created(assetTypeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(assetTypeEntityModel);
    }

    @GetMapping("/assetTypes/{assetTypeId}")
    public ResponseEntity<EntityModel<AssetType>> getAssetTypeById(@PathVariable final String assetTypeId) {
        try {
            AssetType assetType = assetTypeService.getAssetTypeById(assetTypeId);
            EntityModel<AssetType> assetTypeEntityModel = toModel(assetType);

            return ResponseEntity.ok().body(assetTypeEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/assetTypes/{assetTypeId}")
    public ResponseEntity<EntityModel<AssetType>> updateAssetType(@RequestBody final AssetType assetTypeRequest,
                                                                  @PathVariable final String assetTypeId) {
        try {
            AssetType updatedAssetType = assetTypeService.updateAssetType(assetTypeId, assetTypeRequest);
            EntityModel<AssetType> assetTypeEntityModel = toModel(updatedAssetType);

            return ResponseEntity.ok().body(assetTypeEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/assetTypes/{assetTypeId}")
    public ResponseEntity<String> deleteAssetType(@PathVariable final String assetTypeId) {
        try {
            assetTypeService.deleteAssetType(assetTypeId);
            return ResponseEntity.ok().body("Asset type deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<AssetType> toModel(final AssetType assetType) {
        return EntityModel.of(assetType,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssetTypeController.class)
                                                                        .getAssetTypeById(assetType.getAssetTypeId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssetTypeController.class)
                                                                        .getAllAssetTypes()).withRel("assetTypes"));
    }

    private CollectionModel<EntityModel<AssetType>> toCollectionModel(final Iterable<? extends AssetType> assetTypesIterable) {
        List<EntityModel<AssetType>> assetTypes = StreamSupport.stream(assetTypesIterable.spliterator(), false)
                                                               .map(this::toModel)
                                                               .toList();

        return CollectionModel.of(assetTypes,
                                  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssetTypeController.class)
                                                                            .getAllAssetTypes())
                                                   .withSelfRel());
    }
}
