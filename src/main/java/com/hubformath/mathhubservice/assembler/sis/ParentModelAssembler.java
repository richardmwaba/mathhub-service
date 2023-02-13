package com.hubformath.mathhubservice.assembler.sis;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controller.sis.ParentController;
import com.hubformath.mathhubservice.dto.sis.ParentDto;

@Component
public class ParentModelAssembler implements RepresentationModelAssembler<ParentDto, EntityModel<ParentDto>> {
    @Override
    public EntityModel<ParentDto> toModel(ParentDto parent) {
        return EntityModel.of(parent,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ParentController.class).getParentById(parent.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ParentController.class).getAllParents()).withRel("parents"));
    }

    @Override
    public CollectionModel<EntityModel<ParentDto>> toCollectionModel(
            Iterable<? extends ParentDto> parents) {
        List<EntityModel<ParentDto>> parentList = StreamSupport.stream(parents.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(parentList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ParentController.class)
                .getAllParents())
                .withSelfRel());
    }
}
