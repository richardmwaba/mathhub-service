package com.hubformath.mathhubservice.services.sis;

import java.util.List;

import com.hubformath.mathhubservice.models.sis.Address;

public interface IAddressService {
    List<Address> getAllAddresses();

    Address createAddress(Address address);

    Address getAddressById(Long id);

    Address updateAddress(Long id, Address address);

    void deleteAddress(Long id);
}
