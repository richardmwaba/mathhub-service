package com.hubformath.mathhubservice.controller.sis;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.sis.AddressDto;
import com.hubformath.mathhubservice.model.sis.Address;
import com.hubformath.mathhubservice.service.sis.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/v1/sis")
public class AddressController {

    private final ModelMapper modelMapper;

    private final AddressService addressService;

    @Autowired
    public AddressController(final ModelMapperConfig modelMapperConfig, final AddressService addressService) {
        this.modelMapper = modelMapperConfig.createModelMapper();
        this.addressService = addressService;
    }

    @GetMapping("/addresses")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<CollectionModel<EntityModel<AddressDto>>> getAllAddresses() {
        List<AddressDto> addresses = addressService.getAllAddresses().stream()
                                                   .map(address -> modelMapper.map(address, AddressDto.class))
                                                   .toList();
        CollectionModel<EntityModel<AddressDto>> addressCollectionModel = toCollectionModel(addresses);

        return ResponseEntity.ok().body(addressCollectionModel);
    }

    @PostMapping("/addresses")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<AddressDto>> newAddress(@RequestBody final AddressDto addressDto) {
        Address addressRequest = modelMapper.map(addressDto, Address.class);
        Address newAddress = addressService.createAddress(addressRequest);
        EntityModel<AddressDto> addressEntityModel = toModel(modelMapper.map(newAddress, AddressDto.class));

        return ResponseEntity.created(addressEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(addressEntityModel);
    }

    @GetMapping("/addresses/{addressId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<AddressDto>> getAddressById(@PathVariable final UUID addressId) {
        try {
            Address address = addressService.getAddressById(addressId);
            EntityModel<AddressDto> addressEntityModel = toModel(modelMapper.map(address, AddressDto.class));
            return ResponseEntity.ok().body(addressEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/addresses/{addressId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<AddressDto>> replaceAddress(@RequestBody final AddressDto addressDto,
                                                                  @PathVariable final UUID addressId) {
        try {
            Address addressRequest = modelMapper.map(addressDto, Address.class);
            Address updatedAddress = addressService.updateAddress(addressId, addressRequest);
            EntityModel<AddressDto> addressEntityModel = toModel(modelMapper.map(updatedAddress, AddressDto.class));
            return ResponseEntity.ok().body(addressEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/addresses/{addressId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<String> deleteAddress(@PathVariable final UUID addressId) {
        try {
            addressService.deleteAddress(addressId);
            return ResponseEntity.ok().body("Address deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<AddressDto> toModel(final AddressDto address) {
        return EntityModel.of(address,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressController.class)
                                                                        .getAddressById(address.getAddressId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressController.class)
                                                                        .getAllAddresses()).withRel("addresses"));
    }

    private CollectionModel<EntityModel<AddressDto>> toCollectionModel(final Iterable<? extends AddressDto> addresses) {
        List<EntityModel<AddressDto>> addressList = StreamSupport.stream(addresses.spliterator(), false)
                                                                 .map(this::toModel)
                                                                 .toList();

        return CollectionModel.of(addressList,
                                  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressController.class)
                                                                            .getAllAddresses())
                                                   .withSelfRel());
    }
}