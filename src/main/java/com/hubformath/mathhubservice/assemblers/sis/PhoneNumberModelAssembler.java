package com.hubformath.mathhubservice.assemblers.sis;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.sis.PhoneNumberController;
import com.hubformath.mathhubservice.dtos.sis.PhoneNumberDto;

@Component
public class PhoneNumberModelAssembler implements RepresentationModelAssembler<PhoneNumberDto, EntityModel<PhoneNumberDto>> {
    @Override
    public EntityModel<PhoneNumberDto> toModel(PhoneNumberDto phoneNumber) {
        return EntityModel.of(phoneNumber,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PhoneNumberController.class).getPhoneNumberById(phoneNumber.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PhoneNumberController.class).getAllPhoneNumbers()).withRel("phoneNumbers"));
    }

    @Override
    public CollectionModel<EntityModel<PhoneNumberDto>> toCollectionModel(
            Iterable<? extends PhoneNumberDto> phoneNumbers) {
        List<EntityModel<PhoneNumberDto>> phoneNumberList = StreamSupport.stream(phoneNumbers.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(phoneNumberList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PhoneNumberController.class)
                .getAllPhoneNumbers())
                .withSelfRel());
    }
}
