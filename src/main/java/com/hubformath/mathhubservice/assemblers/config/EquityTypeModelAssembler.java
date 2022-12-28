package com.hubformath.mathhubservice.assemblers.config;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.config.EquityTypeController;
import com.hubformath.mathhubservice.dtos.config.EquityTypeDto;

@Component
public class EquityTypeModelAssembler implements RepresentationModelAssembler<EquityTypeDto, EntityModel<EquityTypeDto>> {
    @Override
    public EntityModel<EquityTypeDto> toModel(EquityTypeDto assessmentType) {

        return EntityModel.of(assessmentType,
            linkTo(methodOn(EquityTypeController.class).getEquityTypeById(assessmentType.getId())).withSelfRel(),
            linkTo(methodOn(EquityTypeController.class).getAllEquityTypes()).withRel("equityTypes"));
    }

    @Override
    public CollectionModel<EntityModel<EquityTypeDto>> toCollectionModel(
        Iterable<? extends EquityTypeDto> assessmentTypes) {
        List<EntityModel<EquityTypeDto>> assessmentTypeList = StreamSupport.stream(assessmentTypes.spliterator(), false)
            .map(this::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(assessmentTypeList, linkTo(methodOn(EquityTypeController.class)
              .getAllEquityTypes())
              .withSelfRel());
    }
}
