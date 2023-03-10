package com.hubformath.mathhubservice.controller.systemconfig;

import java.util.List;
import java.util.stream.StreamSupport;

import com.hubformath.mathhubservice.service.systemconfig.AssetTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hubformath.mathhubservice.dto.systemconfig.AssetTypeDto;
import com.hubformath.mathhubservice.model.systemconfig.AssetType;

@RestController
@RequestMapping(path="/api/v1/systemconfig/ops")
public class AssetTypeController {

    private final ModelMapper modelMapper;

    private final AssetTypeService assetTypeService;

    @Autowired
    public AssetTypeController(final ModelMapper modelMapper, final AssetTypeService assetTypeService) {
        this.modelMapper = modelMapper;
        this.assetTypeService = assetTypeService;
    }

    @GetMapping("/assetTypes")
    public ResponseEntity<CollectionModel<EntityModel<AssetTypeDto>>> getAllAssetTypes() {
        List<AssetTypeDto> assetTypes = assetTypeService.getAllAssetTypes().stream()
                .map(assetType -> modelMapper.map(assetType, AssetTypeDto.class))
                .toList();
        CollectionModel<EntityModel<AssetTypeDto>> assetTypeCollectionModel = toCollectionModel(assetTypes);

        return ResponseEntity.ok().body(assetTypeCollectionModel);
    }

    @PostMapping("/assetTypes")
    public ResponseEntity<EntityModel<AssetTypeDto>> newAssetType(@RequestBody final AssetTypeDto assetTypeDto) {
        AssetType assetTypeRequest = modelMapper.map(assetTypeDto, AssetType.class);
        AssetType newAssetType = assetTypeService.createAssetType(assetTypeRequest);
        EntityModel<AssetTypeDto> assetTypeEntityModel = toModel(modelMapper.map(newAssetType, AssetTypeDto.class));

        return ResponseEntity.created(assetTypeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(assetTypeEntityModel);
    }

    @GetMapping("/assetTypes/{id}")
    public ResponseEntity<EntityModel<AssetTypeDto>> getAssetTypeById(@PathVariable final Long id) {
        AssetType assetType = assetTypeService.getAssetTypeById(id);
        EntityModel<AssetTypeDto> assetTypeEntityModel = toModel(modelMapper.map(assetType, AssetTypeDto.class));

        return ResponseEntity.ok().body(assetTypeEntityModel);
    }

    @PutMapping("/assetTypes/{id}")
    public ResponseEntity<EntityModel<AssetTypeDto>> replaceAssetType(
            @RequestBody final AssetTypeDto assetTypeDto,
            @PathVariable final Long id) {
        AssetType assetTypeRequest = modelMapper.map(assetTypeDto, AssetType.class);
        AssetType updatedAssetType = assetTypeService.updateAssetType(id, assetTypeRequest);
        EntityModel<AssetTypeDto> assetTypeEntityModel = toModel(modelMapper.map(updatedAssetType, AssetTypeDto.class));

        return ResponseEntity.ok().body(assetTypeEntityModel);
    }

    @DeleteMapping("/assetTypes/{id}")
    public ResponseEntity<String> deleteAssetType(@PathVariable final Long id) {
        assetTypeService.deleteAssetType(id);
        return ResponseEntity.ok().body("Asset type deleted successfully");
    }

    private EntityModel<AssetTypeDto> toModel(final AssetTypeDto assetType) {
        return EntityModel.of(assetType,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssetTypeController.class).getAssetTypeById(assetType.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssetTypeController.class).getAllAssetTypes()).withRel("assetTypes"));
    }

    private CollectionModel<EntityModel<AssetTypeDto>> toCollectionModel(final Iterable<? extends AssetTypeDto> assetTypes) {
        List<EntityModel<AssetTypeDto>> assetTypeList = StreamSupport.stream(assetTypes.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(assetTypeList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssetTypeController.class)
                        .getAllAssetTypes())
                .withSelfRel());
    }
}
