package com.hubformath.mathhubservice.controller.sis;

import java.util.List;
import java.util.stream.StreamSupport;

import com.hubformath.mathhubservice.service.sis.PhoneNumberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hubformath.mathhubservice.dto.sis.PhoneNumberDto;
import com.hubformath.mathhubservice.model.sis.PhoneNumber;

@RestController
@RequestMapping(path = "/api/v1/sis")
public class PhoneNumberController {

    private final ModelMapper modelMapper;

    private final PhoneNumberService phoneNumberService;

    @Autowired
    public PhoneNumberController(final ModelMapper modelMapper, final PhoneNumberService phoneNumberService) {
        this.modelMapper = modelMapper;
        this.phoneNumberService = phoneNumberService;
    }

    @GetMapping("/phoneNumbers")
    public ResponseEntity<CollectionModel<EntityModel<PhoneNumberDto>>> getAllPhoneNumbers() {
        List<PhoneNumberDto> phoneNumbers = phoneNumberService.getAllPhoneNumbers().stream()
                .map(phoneNumber -> modelMapper.map(phoneNumber, PhoneNumberDto.class))
                .toList();
        CollectionModel<EntityModel<PhoneNumberDto>> phoneNumberCollectionModel = toCollectionModel(phoneNumbers);

        return ResponseEntity.ok().body(phoneNumberCollectionModel);
    }

    @PostMapping("/phoneNumbers")
    public ResponseEntity<EntityModel<PhoneNumberDto>> newPhoneNumber(@RequestBody final PhoneNumberDto phoneNumberDto) {
        PhoneNumber phoneNumberRequest = modelMapper.map(phoneNumberDto, PhoneNumber.class);
        PhoneNumber newPhoneNumber = phoneNumberService.createPhoneNumber(phoneNumberRequest);

        EntityModel<PhoneNumberDto> phoneNumberEntityModel = toModel(modelMapper.map(newPhoneNumber, PhoneNumberDto.class));

        return ResponseEntity.created(phoneNumberEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(phoneNumberEntityModel);
    }

    @GetMapping("/phoneNumbers/{id}")
    public ResponseEntity<EntityModel<PhoneNumberDto>> getPhoneNumberById(@PathVariable final Long id) {
        PhoneNumber phoneNumber = phoneNumberService.getPhoneNumberById(id);
        EntityModel<PhoneNumberDto> phoneNumberEntityModel = toModel(modelMapper.map(phoneNumber, PhoneNumberDto.class));

        return ResponseEntity.ok().body(phoneNumberEntityModel);
    }

    @PutMapping("/phoneNumbers/{id}")
    public ResponseEntity<EntityModel<PhoneNumberDto>> replacePhoneNumber(
            @RequestBody final PhoneNumberDto phoneNumberDto,
            @PathVariable final Long id) {
        PhoneNumber phoneNumberRequest = modelMapper.map(phoneNumberDto, PhoneNumber.class);
        PhoneNumber updatedPhoneNumber = phoneNumberService.updatePhoneNumber(id, phoneNumberRequest);

        EntityModel<PhoneNumberDto> phoneNumberEntityModel = toModel(modelMapper.map(updatedPhoneNumber, PhoneNumberDto.class));

        return ResponseEntity.ok().body(phoneNumberEntityModel);

    }

    @DeleteMapping("/phoneNumbers/{id}")
    public ResponseEntity<String> deletePhoneNumber(@PathVariable final Long id) {
        phoneNumberService.deletePhoneNumber(id);
        return ResponseEntity.ok().body("PhoneNumber deleted successfully");
    }

    private EntityModel<PhoneNumberDto> toModel(final PhoneNumberDto phoneNumber) {
        return EntityModel.of(phoneNumber,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PhoneNumberController.class).getPhoneNumberById(phoneNumber.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PhoneNumberController.class).getAllPhoneNumbers()).withRel("phoneNumbers"));
    }

    private CollectionModel<EntityModel<PhoneNumberDto>> toCollectionModel(final Iterable<? extends PhoneNumberDto> phoneNumbers) {
        List<EntityModel<PhoneNumberDto>> phoneNumberList = StreamSupport.stream(phoneNumbers.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(phoneNumberList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PhoneNumberController.class)
                        .getAllPhoneNumbers())
                .withSelfRel());
    }

}