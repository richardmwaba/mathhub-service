package com.hubformath.mathhubservice.service.sis;

import java.util.List;

import com.hubformath.mathhubservice.model.sis.PhoneNumber;

public interface IPhoneNumberService {
    List<PhoneNumber> getAllPhoneNumbers();

    PhoneNumber createPhoneNumber(PhoneNumber phoneNumber);

    PhoneNumber getPhoneNumberById(Long id);

    PhoneNumber updatePhoneNumber(Long id, PhoneNumber phoneNumber);

    void deletePhoneNumber(Long id);
}
