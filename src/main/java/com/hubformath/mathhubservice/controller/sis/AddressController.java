package com.hubformath.mathhubservice.controller.sis;

import java.util.List;


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

import com.hubformath.mathhubservice.assembler.sis.AddressModelAssembler;
import com.hubformath.mathhubservice.dto.sis.AddressDto;
import com.hubformath.mathhubservice.model.sis.Address;
import com.hubformath.mathhubservice.service.sis.IAddressService;

@RestController
@RequestMapping(path = "/api/v1/sis")
public class AddressController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IAddressService addressService;

    @Autowired
    private AddressModelAssembler addressModelAssembler;

    public AddressController() {
        super();
    }

    @GetMapping("/addresses")
    public ResponseEntity<CollectionModel<EntityModel<AddressDto>>> getAllAddresses() {
        List<AddressDto> addresses = addressService.getAllAddresses().stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .toList();

        CollectionModel<EntityModel<AddressDto>> addressCollectionModel = addressModelAssembler
                .toCollectionModel(addresses);

        return ResponseEntity.ok().body(addressCollectionModel);
    }

    @PostMapping("/addresses")
    public ResponseEntity<EntityModel<AddressDto>> newAddress(
            @RequestBody AddressDto addressDto) {
        Address addressRequest = modelMapper.map(addressDto, Address.class);
        Address newAddress = addressService.createAddress(addressRequest);

        EntityModel<AddressDto> addressEntityModel = addressModelAssembler
                .toModel(modelMapper.map(newAddress, AddressDto.class));

        return ResponseEntity.created(addressEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(addressEntityModel);
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<EntityModel<AddressDto>> getAddressById(@PathVariable Long id) {
        Address address = addressService.getAddressById(id);

        EntityModel<AddressDto> addressEntityModel = addressModelAssembler
                .toModel(modelMapper.map(address, AddressDto.class));

        return ResponseEntity.ok().body(addressEntityModel);
    }

    @PutMapping("/addresses/{id}")
    public ResponseEntity<EntityModel<AddressDto>> replaceAddress(
            @RequestBody AddressDto addressDto,
            @PathVariable Long id) {
        Address addressRequest = modelMapper.map(addressDto, Address.class);
        Address updatedAddress = addressService.updateAddress(id, addressRequest);

        EntityModel<AddressDto> addressEntityModel = addressModelAssembler
                .toModel(modelMapper.map(updatedAddress, AddressDto.class));

        return ResponseEntity.ok().body(addressEntityModel);

    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);

        return ResponseEntity.ok().body("Address deleted sucessfully");
    }
}
