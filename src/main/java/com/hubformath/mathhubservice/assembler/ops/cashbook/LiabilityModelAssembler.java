package com.hubformath.mathhubservice.assembler.ops.cashbook;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controller.ops.cashbook.LiabilityController;
import com.hubformath.mathhubservice.dto.ops.cashbook.LiabilityDto;

@Component
public class LiabilityModelAssembler implements RepresentationModelAssembler<LiabilityDto, EntityModel<LiabilityDto>> {
    @Override
    public EntityModel<LiabilityDto> toModel(LiabilityDto liability) {
        return EntityModel.of(liability,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LiabilityController.class).getLiabilityById(liability.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LiabilityController.class).getAllLiabilities()).withRel("liabilities"));
    }

    @Override
    public CollectionModel<EntityModel<LiabilityDto>> toCollectionModel(
            Iterable<? extends LiabilityDto> liabilities) {
        List<EntityModel<LiabilityDto>> liabilityList = StreamSupport.stream(liabilities.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(liabilityList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LiabilityController.class)
                .getAllLiabilities())
                .withSelfRel());
    }
}

