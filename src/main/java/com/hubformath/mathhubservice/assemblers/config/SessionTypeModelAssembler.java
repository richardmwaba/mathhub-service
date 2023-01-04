package com.hubformath.mathhubservice.assemblers.config;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.config.SessionTypeController;
import com.hubformath.mathhubservice.dtos.config.SessionTypeDto;

@Component
public class SessionTypeModelAssembler
        implements RepresentationModelAssembler<SessionTypeDto, EntityModel<SessionTypeDto>> {
    @Override
    public EntityModel<SessionTypeDto> toModel(SessionTypeDto sessionType) {

        return EntityModel.of(sessionType,
                linkTo(methodOn(SessionTypeController.class).getSessionTypeById(sessionType.getId())).withSelfRel(),
                linkTo(methodOn(SessionTypeController.class).getAllSessionTypes()).withRel("sessionTypes"));
    }

    @Override
    public CollectionModel<EntityModel<SessionTypeDto>> toCollectionModel(
            Iterable<? extends SessionTypeDto> sessionTypes) {
        List<EntityModel<SessionTypeDto>> sessionTypeList = StreamSupport.stream(sessionTypes.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(sessionTypeList, linkTo(methodOn(SessionTypeController.class)
                .getAllSessionTypes())
                .withSelfRel());
    }
}