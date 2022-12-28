package com.hubformath.mathhubservice.assemblers.config;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.config.LiabilityTypeController;
import com.hubformath.mathhubservice.dtos.config.LiabilityTypeDto;

@Component
public class LiabilityTypeModelAssembler implements RepresentationModelAssembler<LiabilityTypeDto, EntityModel<LiabilityTypeDto>> {
    @Override
    public EntityModel<LiabilityTypeDto> toModel(LiabilityTypeDto assessmentType) {

        return EntityModel.of(assessmentType,
            linkTo(methodOn(LiabilityTypeController.class).getLiabilityTypeById(assessmentType.getId())).withSelfRel(),
            linkTo(methodOn(LiabilityTypeController.class).getAllLiabilityTypes()).withRel("liabilityTypes"));
    }

    @Override
    public CollectionModel<EntityModel<LiabilityTypeDto>> toCollectionModel(
        Iterable<? extends LiabilityTypeDto> assessmentTypes) {
        List<EntityModel<LiabilityTypeDto>> assessmentTypeList = StreamSupport.stream(assessmentTypes.spliterator(), false)
            .map(this::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(assessmentTypeList, linkTo(methodOn(LiabilityTypeController.class)
              .getAllLiabilityTypes())
              .withSelfRel());
    }
}
