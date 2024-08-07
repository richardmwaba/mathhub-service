package com.hubformath.mathhubservice.service.sis;

import com.hubformath.mathhubservice.model.sis.Address;
import com.hubformath.mathhubservice.repository.sis.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(final AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressById(final String addressId) {
        return addressRepository.findById(addressId).orElseThrow();
    }

    public Address createAddress(final Address addressRequest) {
        return addressRepository.save(addressRequest);
    }

    public Address updateAddress(final String addressId, final Address addressRequest) {
        return addressRepository.findById(addressId)
                                .map(address -> {
                                    Optional.ofNullable(addressRequest.getType())
                                            .ifPresent(address::setType);
                                    Optional.ofNullable(addressRequest.getFirstAddress())
                                            .ifPresent(address::setFirstAddress);
                                    Optional.ofNullable(addressRequest.getSecondAddress())
                                            .ifPresent(address::setSecondAddress);
                                    Optional.ofNullable(addressRequest.getThirdAddress())
                                            .ifPresent(address::setThirdAddress);
                                    Optional.ofNullable(addressRequest.getCity()).ifPresent(address::setCity);
                                    Optional.ofNullable(addressRequest.getProvince()).ifPresent(address::setProvince);
                                    Optional.ofNullable(addressRequest.getZipCode()).ifPresent(address::setZipCode);
                                    return addressRepository.save(address);
                                })
                                .orElseThrow();
    }

    public void deleteAddress(final String addressId) {
        Address address = addressRepository.findById(addressId)
                                           .orElseThrow();

        addressRepository.delete(address);
    }
}
