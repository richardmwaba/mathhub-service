package com.hubformath.mathhubservice.assembler.systemconfig;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controller.systemconfig.IncomeTypeController;
import com.hubformath.mathhubservice.dto.systemconfig.IncomeTypeDto;

@Component
public class IncomeTypeModelAssembler implements RepresentationModelAssembler<IncomeTypeDto, EntityModel<IncomeTypeDto>> {
    @Override
    public EntityModel<IncomeTypeDto> toModel(IncomeTypeDto incomeType) {
        return EntityModel.of(incomeType,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IncomeTypeController.class).getIncomeTypeById(incomeType.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IncomeTypeController.class).getAllIncomeTypes()).withRel("incomeTypes"));
    }

    @Override
    public CollectionModel<EntityModel<IncomeTypeDto>> toCollectionModel(
            Iterable<? extends IncomeTypeDto> incomeTypes) {
        List<EntityModel<IncomeTypeDto>> incomeTypeList = StreamSupport.stream(incomeTypes.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(incomeTypeList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IncomeTypeController.class)
                .getAllIncomeTypes())
                .withSelfRel());
    }
}
