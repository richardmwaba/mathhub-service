package com.hubformath.mathhubservice.assemblers.config;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.config.AssetTypeController;
import com.hubformath.mathhubservice.models.config.AssetType;

@Component
public class AssetTypeModelAssembler implements RepresentationModelAssembler<AssetType, EntityModel<AssetType>> {
    @Override
    public EntityModel<AssetType> toModel(AssetType assetType) {

    return EntityModel.of(assetType, //
        linkTo(methodOn(AssetTypeController.class).one(assetType.getId())).withSelfRel(),
        linkTo(methodOn(AssetTypeController.class).all()).withRel("assetTypes"));
    }
}
