package com.hubformath.mathhubservice.assemblers.sis;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.sis.AddressController;
import com.hubformath.mathhubservice.dtos.sis.AddressDto;

@Component
public class AddressModelAssembler implements RepresentationModelAssembler<AddressDto, EntityModel<AddressDto>> {
    @Override
    public EntityModel<AddressDto> toModel(AddressDto address) {
        return EntityModel.of(address,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressController.class).getAddressById(address.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressController.class).getAllAddresses()).withRel("addresss"));
    }

    @Override
    public CollectionModel<EntityModel<AddressDto>> toCollectionModel(
            Iterable<? extends AddressDto> addresss) {
        List<EntityModel<AddressDto>> addressList = StreamSupport.stream(addresss.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(addressList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressController.class)
                .getAllAddresses())
                .withSelfRel());
    }
}
