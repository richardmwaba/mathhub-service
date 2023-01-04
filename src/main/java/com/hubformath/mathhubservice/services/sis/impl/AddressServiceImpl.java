package com.hubformath.mathhubservice.services.sis.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.models.sis.Address;
import com.hubformath.mathhubservice.repositories.sis.AddressRepository;
import com.hubformath.mathhubservice.services.sis.IAddressService;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@Service
public class AddressServiceImpl implements IAddressService {
    private final AddressRepository addressRepository;
    private final String notFoundItemName;
    
    public AddressServiceImpl(AddressRepository addressRepository) {
        super();
        this.addressRepository = addressRepository;
        this.notFoundItemName = "address";
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address getAddressById(Long id) {
        Optional<Address> address = addressRepository.findById(id);

        if(address.isPresent()){
            return address.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public Address createAddress(Address addressRequest) {
        return addressRepository.save(addressRequest);
    }

    @Override
    public Address updateAddress(Long id, Address addressRequest) {
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

    @Override
    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        addressRepository.delete(address);
    }
}
