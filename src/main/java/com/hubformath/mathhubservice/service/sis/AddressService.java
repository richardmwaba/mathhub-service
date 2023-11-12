package com.hubformath.mathhubservice.service.sis;

import com.hubformath.mathhubservice.model.sis.Address;
import com.hubformath.mathhubservice.repository.sis.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(final AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressById(final UUID addressId) {
        return addressRepository.findById(addressId).orElseThrow();
    }

    public Address createAddress(final Address addressRequest) {
        return addressRepository.save(addressRequest);
    }

    public Address updateAddress(final UUID addressId, final Address addressRequest) {
        return addressRepository.findById(addressId)
                                .map(address -> {
                                    Optional.ofNullable(addressRequest.getAddressType())
                                            .ifPresent(address::setAddressType);
                                    Optional.ofNullable(addressRequest.getAddressLine1())
                                            .ifPresent(address::setAddressLine1);
                                    Optional.ofNullable(addressRequest.getAddressLine2())
                                            .ifPresent(address::setAddressLine2);
                                    Optional.ofNullable(addressRequest.getAddressLine3())
                                            .ifPresent(address::setAddressLine3);
                                    Optional.ofNullable(addressRequest.getCity()).ifPresent(address::setCity);
                                    Optional.ofNullable(addressRequest.getProvince()).ifPresent(address::setProvince);
                                    Optional.ofNullable(addressRequest.getZipCode()).ifPresent(address::setZipCode);
                                    return addressRepository.save(address);
                                })
                                .orElseThrow();
    }

    public void deleteAddress(final UUID addressId) {
        Address address = addressRepository.findById(addressId)
                                           .orElseThrow();

        addressRepository.delete(address);
    }
}
