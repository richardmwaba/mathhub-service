package com.hubformath.mathhubservice.assemblers.ops.cashbook;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.ops.cashbook.LiabilityController;
import com.hubformath.mathhubservice.dtos.ops.cashbook.LiabilityDto;

@Component
public class LiabilityModelAssembler
        implements RepresentationModelAssembler<LiabilityDto, EntityModel<LiabilityDto>> {
    @Override
    public EntityModel<LiabilityDto> toModel(LiabilityDto liability) {

        return EntityModel.of(liability,
                linkTo(methodOn(LiabilityController.class).getLiabilityById(liability.getId())).withSelfRel(),
                linkTo(methodOn(LiabilityController.class).getAllLiabilities()).withRel("liabilities"));
    }

    @Override
    public CollectionModel<EntityModel<LiabilityDto>> toCollectionModel(
            Iterable<? extends LiabilityDto> liabilities) {
        List<EntityModel<LiabilityDto>> liabilityList = StreamSupport.stream(liabilities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(liabilityList, linkTo(methodOn(LiabilityController.class)
                .getAllLiabilities())
                .withSelfRel());
    }
}

