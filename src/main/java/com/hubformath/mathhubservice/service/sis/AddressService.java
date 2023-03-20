package com.hubformath.mathhubservice.service.sis;

import com.hubformath.mathhubservice.model.sis.Address;
import com.hubformath.mathhubservice.repository.sis.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(final AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressById(final Long id) {
        return addressRepository.findById(id).orElseThrow();
    }

    public Address createAddress(final Address addressRequest) {
        return addressRepository.save(addressRequest);
    }

    public Address updateAddress(final Long id, final Address addressRequest) {
        return addressRepository.findById(id) 
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

    public void deleteAddress(final Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow();

        addressRepository.delete(address);
    }
}
