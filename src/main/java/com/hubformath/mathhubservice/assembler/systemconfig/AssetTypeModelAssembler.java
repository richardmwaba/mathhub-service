package com.hubformath.mathhubservice.assembler.systemconfig;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controller.systemconfig.AssetTypeController;
import com.hubformath.mathhubservice.dto.systemconfig.AssetTypeDto;

@Component
public class AssetTypeModelAssembler
        implements RepresentationModelAssembler<AssetTypeDto, EntityModel<AssetTypeDto>> {
    @Override
    public EntityModel<AssetTypeDto> toModel(AssetTypeDto assetType) {
        return EntityModel.of(assetType,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssetTypeController.class).getAssetTypeById(assetType.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssetTypeController.class).getAllAssetTypes()).withRel("assetTypes"));
    }

    @Override
    public CollectionModel<EntityModel<AssetTypeDto>> toCollectionModel(
            Iterable<? extends AssetTypeDto> assetTypes) {
        List<EntityModel<AssetTypeDto>> assetTypeList = StreamSupport.stream(assetTypes.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(assetTypeList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssetTypeController.class)
                .getAllAssetTypes())
                .withSelfRel());
    }
}
