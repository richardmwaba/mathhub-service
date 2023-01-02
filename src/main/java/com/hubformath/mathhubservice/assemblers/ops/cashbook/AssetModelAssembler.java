package com.hubformath.mathhubservice.assemblers.ops.cashbook;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.ops.cashbook.AssetController;
import com.hubformath.mathhubservice.dtos.ops.cashbook.AssetDto;

@Component
public class AssetModelAssembler
    implements RepresentationModelAssembler<AssetDto, EntityModel<AssetDto>> {
        @Override
        public EntityModel<AssetDto> toModel(AssetDto asset) {
    
            return EntityModel.of(asset,
                linkTo(methodOn(AssetController.class).getAssetById(asset.getId())).withSelfRel(),
                linkTo(methodOn(AssetController.class).getAllAssets()).withRel("assets"));
        }
    
        @Override
        public CollectionModel<EntityModel<AssetDto>> toCollectionModel(
            Iterable<? extends AssetDto> assets) {
            List<EntityModel<AssetDto>> assetList = StreamSupport.stream(assets.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
    
            return CollectionModel.of(assetList, linkTo(methodOn(AssetController.class)
                  .getAllAssets())
                  .withSelfRel());
        }    
}
