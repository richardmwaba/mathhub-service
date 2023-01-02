package com.hubformath.mathhubservice.assemblers.ops.cashbook;

import com.hubformath.mathhubservice.controllers.ops.cashbook.EquityController;
import com.hubformath.mathhubservice.dtos.ops.cashbook.EquityDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EquityModelAssembler
        implements RepresentationModelAssembler<EquityDto, EntityModel<EquityDto>> {

    @Override
    public EntityModel<EquityDto> toModel(EquityDto equity) {

        return EntityModel.of(equity,
                linkTo(methodOn(EquityController.class).getEquityById(equity.getId())).withSelfRel(),
                linkTo(methodOn(EquityController.class).getAllEquity()).withRel("equity"));
    }

    @Override
    public CollectionModel<EntityModel<EquityDto>> toCollectionModel(
            Iterable<? extends EquityDto> equity) {
        List<EntityModel<EquityDto>> equityList = StreamSupport.stream(equity.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(equityList, linkTo(methodOn(EquityController.class)
                .getAllEquity())
                .withSelfRel());
    }
}
