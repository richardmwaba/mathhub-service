package com.hubformath.mathhubservice.assembler.ops.cashbook;

import com.hubformath.mathhubservice.controller.ops.cashbook.EquityController;
import com.hubformath.mathhubservice.dto.ops.cashbook.EquityDto;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class EquityModelAssembler implements RepresentationModelAssembler<EquityDto, EntityModel<EquityDto>> {
    @Override
    public EntityModel<EquityDto> toModel(EquityDto equity) {
        return EntityModel.of(equity,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EquityController.class).getEquityById(equity.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EquityController.class).getAllEquity()).withRel("equity"));
    }

    @Override
    public CollectionModel<EntityModel<EquityDto>> toCollectionModel(
            Iterable<? extends EquityDto> equity) {
        List<EntityModel<EquityDto>> equityList = StreamSupport.stream(equity.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(equityList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EquityController.class)
                .getAllEquity())
                .withSelfRel());
    }
}
