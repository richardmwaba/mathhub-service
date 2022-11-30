package com.hubformath.mathhubservice.assemblers.config;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.config.AssetTypeController;
import com.hubformath.mathhubservice.dtos.config.AssetTypeDto;

@Component
public class AssetTypeModelAssembler 
    implements RepresentationModelAssembler<AssetTypeDto, EntityModel<AssetTypeDto>> {
    @Override
    public EntityModel<AssetTypeDto> toModel(AssetTypeDto assetType) {

        return EntityModel.of(assetType,
            linkTo(methodOn(AssetTypeController.class).getAssetTypeById(assetType.getId())).withSelfRel(),
            linkTo(methodOn(AssetTypeController.class).getAllAssetTypes()).withRel("assetTypes"));
    }

    @Override
    public CollectionModel<EntityModel<AssetTypeDto>> toCollectionModel(
        Iterable<? extends AssetTypeDto> assetTypes) {
        List<EntityModel<AssetTypeDto>> assetTypeList = StreamSupport.stream(assetTypes.spliterator(), false)
            .map(this::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(assetTypeList, linkTo(methodOn(AssetTypeController.class)
              .getAllAssetTypes())
              .withSelfRel());
    }
}
