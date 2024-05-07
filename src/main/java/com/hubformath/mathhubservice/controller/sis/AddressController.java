package com.hubformath.mathhubservice.controller.sis;

import com.hubformath.mathhubservice.controller.util.CollectionModelUtils;
import com.hubformath.mathhubservice.model.sis.Address;
import com.hubformath.mathhubservice.service.sis.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
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

@RestController
@RequestMapping(path = "/api/v1/sis")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/addresses")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<CollectionModel<?>> getAllAddresses() {
        List<Address> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok().body(toCollectionModel(addresses));
    }

    @PostMapping("/addresses")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<Address>> newAddress(@RequestBody Address addressRequest) {
        EntityModel<Address> newAddress = toModel(addressService.createAddress(addressRequest));
        return ResponseEntity.created(newAddress.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(newAddress);
    }

    @GetMapping("/addresses/{addressId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<Address>> getAddressById(@PathVariable String addressId) {
        try {
            EntityModel<Address> address = toModel(addressService.getAddressById(addressId));
            return ResponseEntity.ok().body(address);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/addresses/{addressId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<Address>> replaceAddress(@RequestBody Address addressRequest,
                                                               @PathVariable String addressId) {
        try {
            EntityModel<Address> updatedAddress = toModel(addressService.updateAddress(addressId, addressRequest));
            return ResponseEntity.ok().body(updatedAddress);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/addresses/{addressId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<String> deleteAddress(@PathVariable String addressId) {
        try {
            addressService.deleteAddress(addressId);
            return ResponseEntity.ok().body("Address deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<Address> toModel(Address address) {
        return EntityModel.of(address,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressController.class)
                                                                        .getAddressById(address.getAddressId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressController.class)
                                                                        .getAllAddresses()).withRel("addresses"));
    }

    private CollectionModel<?> toCollectionModel(List<Address> addressList) {
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressController.class)
                                                              .getAllAddresses()).withSelfRel();
        List<EntityModel<Address>> addresses = addressList.stream()
                                                          .map(this::toModel)
                                                          .toList();

        return addressList.isEmpty()
                ? CollectionModelUtils.getEmptyEmbeddedCollectionModel(Address.class, link)
                : CollectionModel.of(addresses, link);
    }
}