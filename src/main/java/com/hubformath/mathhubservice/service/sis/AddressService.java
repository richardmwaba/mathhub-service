package com.hubformath.mathhubservice.service.sis;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.sis.Address;
import com.hubformath.mathhubservice.repository.sis.AddressRepository;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    private final String notFoundItemName;
    
    public AddressService(final AddressRepository addressRepository) {
        super();
        this.addressRepository = addressRepository;
        this.notFoundItemName = "address";
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressById(final Long id) {
        Optional<Address> address = addressRepository.findById(id);

        if(address.isPresent()){
            return address.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
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
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    public void deleteAddress(final Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        addressRepository.delete(address);
    }
}