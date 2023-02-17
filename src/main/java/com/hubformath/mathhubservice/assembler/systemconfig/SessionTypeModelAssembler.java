package com.hubformath.mathhubservice.assembler.systemconfig;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controller.systemconfig.SessionTypeController;
import com.hubformath.mathhubservice.dto.systemconfig.SessionTypeDto;

@Component
public class SessionTypeModelAssembler implements RepresentationModelAssembler<SessionTypeDto, EntityModel<SessionTypeDto>> {
    @Override
    public EntityModel<SessionTypeDto> toModel(SessionTypeDto sessionType) {
        return EntityModel.of(sessionType,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SessionTypeController.class).getSessionTypeById(sessionType.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SessionTypeController.class).getAllSessionTypes()).withRel("sessionTypes"));
    }

    @Override
    public CollectionModel<EntityModel<SessionTypeDto>> toCollectionModel(
            Iterable<? extends SessionTypeDto> sessionTypes) {
        List<EntityModel<SessionTypeDto>> sessionTypeList = StreamSupport.stream(sessionTypes.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(sessionTypeList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SessionTypeController.class)
                .getAllSessionTypes())
                .withSelfRel());
    }
}