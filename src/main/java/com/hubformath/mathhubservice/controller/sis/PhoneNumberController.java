package com.hubformath.mathhubservice.controller.sis;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hubformath.mathhubservice.assembler.sis.PhoneNumberModelAssembler;
import com.hubformath.mathhubservice.dto.sis.PhoneNumberDto;
import com.hubformath.mathhubservice.model.sis.PhoneNumber;
import com.hubformath.mathhubservice.service.sis.IPhoneNumberService;

@RestController
@RequestMapping(path = "/api/v1/sis")
public class PhoneNumberController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IPhoneNumberService phoneNumberService;

    @Autowired
    private PhoneNumberModelAssembler phoneNumberModelAssembler;

    public PhoneNumberController() {
        super();
    }

    @GetMapping("/phoneNumbers")
    public ResponseEntity<CollectionModel<EntityModel<PhoneNumberDto>>> getAllPhoneNumbers() {
        List<PhoneNumberDto> phoneNumbers = phoneNumberService.getAllPhoneNumbers().stream()
                .map(phoneNumber -> modelMapper.map(phoneNumber, PhoneNumberDto.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<PhoneNumberDto>> phoneNumberCollectionModel = phoneNumberModelAssembler
                .toCollectionModel(phoneNumbers);

        return ResponseEntity.ok().body(phoneNumberCollectionModel);
    }

    @PostMapping("/phoneNumbers")
    public ResponseEntity<EntityModel<PhoneNumberDto>> newPhoneNumber(
            @RequestBody PhoneNumberDto phoneNumberDto) {
        PhoneNumber phoneNumberRequest = modelMapper.map(phoneNumberDto, PhoneNumber.class);
        PhoneNumber newPhoneNumber = phoneNumberService.createPhoneNumber(phoneNumberRequest);

        EntityModel<PhoneNumberDto> phoneNumberEntityModel = phoneNumberModelAssembler
                .toModel(modelMapper.map(newPhoneNumber, PhoneNumberDto.class));

        return ResponseEntity.created(phoneNumberEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(phoneNumberEntityModel);
    }

    @GetMapping("/phoneNumbers/{id}")
    public ResponseEntity<EntityModel<PhoneNumberDto>> getPhoneNumberById(@PathVariable Long id) {
        PhoneNumber phoneNumber = phoneNumberService.getPhoneNumberById(id);

        EntityModel<PhoneNumberDto> phoneNumberEntityModel = phoneNumberModelAssembler
                .toModel(modelMapper.map(phoneNumber, PhoneNumberDto.class));

        return ResponseEntity.ok().body(phoneNumberEntityModel);
    }

    @PutMapping("/phoneNumbers/{id}")
    public ResponseEntity<EntityModel<PhoneNumberDto>> replacePhoneNumber(
            @RequestBody PhoneNumberDto phoneNumberDto,
            @PathVariable Long id) {
        PhoneNumber phoneNumberRequest = modelMapper.map(phoneNumberDto, PhoneNumber.class);
        PhoneNumber updatedPhoneNumber = phoneNumberService.updatePhoneNumber(id, phoneNumberRequest);

        EntityModel<PhoneNumberDto> phoneNumberEntityModel = phoneNumberModelAssembler
                .toModel(modelMapper.map(updatedPhoneNumber, PhoneNumberDto.class));

        return ResponseEntity.ok().body(phoneNumberEntityModel);

    }

    @DeleteMapping("/phoneNumbers/{id}")
    public ResponseEntity<String> deletePhoneNumber(@PathVariable Long id) {
        phoneNumberService.deletePhoneNumber(id);

        return ResponseEntity.ok().body("PhoneNumber deleted sucessfully");
    }
}
