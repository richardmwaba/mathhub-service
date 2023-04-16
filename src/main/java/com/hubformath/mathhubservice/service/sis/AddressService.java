package com.hubformath.mathhubservice.service.sis;

import com.hubformath.mathhubservice.model.sis.Address;
import com.hubformath.mathhubservice.repository.sis.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
                    address.setAddressType(addressRequest.getAddressType());
                    address.setAddressLine1(addressRequest.getAddressLine1());
                    address.setAddressLine2(addressRequest.getAddressLine2());
                    address.setAddressLine3(addressRequest.getAddressLine3());
                    address.setCity(addressRequest.getCity());
                    address.setProvince(addressRequest.getProvince());
                    address.setZipCode(addressRequest.getZipCode());
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
