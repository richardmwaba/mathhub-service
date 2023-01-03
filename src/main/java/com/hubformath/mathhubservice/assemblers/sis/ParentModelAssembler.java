package com.hubformath.mathhubservice.assemblers.sis;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.sis.ParentController;
import com.hubformath.mathhubservice.dtos.sis.ParentDto;

@Component
public class ParentModelAssembler
        implements RepresentationModelAssembler<ParentDto, EntityModel<ParentDto>> {
    @Override
    public EntityModel<ParentDto> toModel(ParentDto parent) {

        return EntityModel.of(parent,
                linkTo(methodOn(ParentController.class).getParentById(parent.getId())).withSelfRel(),
                linkTo(methodOn(ParentController.class).getAllParents()).withRel("parents"));
    }

    @Override
    public CollectionModel<EntityModel<ParentDto>> toCollectionModel(
            Iterable<? extends ParentDto> parents) {
        List<EntityModel<ParentDto>> parentList = StreamSupport.stream(parents.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(parentList, linkTo(methodOn(ParentController.class)
                .getAllParents())
                .withSelfRel());
    }
}
