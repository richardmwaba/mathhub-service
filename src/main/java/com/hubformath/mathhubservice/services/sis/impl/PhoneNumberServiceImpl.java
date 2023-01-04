package com.hubformath.mathhubservice.services.sis.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.models.sis.PhoneNumber;
import com.hubformath.mathhubservice.repositories.sis.PhoneNumberRepository;
import com.hubformath.mathhubservice.services.sis.IPhoneNumberService;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@Service
public class PhoneNumberServiceImpl implements IPhoneNumberService {
    private final PhoneNumberRepository phoneNumberRepository;
    private final String notFoundItemName;
    
    public PhoneNumberServiceImpl(PhoneNumberRepository phoneNumberRepository) {
        super();
        this.phoneNumberRepository = phoneNumberRepository;
        this.notFoundItemName = "phoneNumber";
    }

    @Override
    public List<PhoneNumber> getAllPhoneNumbers() {
        return phoneNumberRepository.findAll();
    }

    @Override
    public PhoneNumber getPhoneNumberById(Long id) {
        Optional<PhoneNumber> phoneNumber = phoneNumberRepository.findById(id);

        if(phoneNumber.isPresent()){
            return phoneNumber.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public PhoneNumber createPhoneNumber(PhoneNumber phoneNumberRequest) {
        return phoneNumberRepository.save(phoneNumberRequest);
    }

    @Override
    public PhoneNumber updatePhoneNumber(Long id, PhoneNumber phoneNumberRequest) {
        return phoneNumberRepository.findById(id) 
                .map(phoneNumber -> {
                    phoneNumber.setType(phoneNumberRequest.getType());
                    phoneNumber.setCountryCode(phoneNumberRequest.getCountryCode());
                    phoneNumber.setNumber(phoneNumberRequest.getNumber());
                    return phoneNumberRepository.save(phoneNumber);
                }) 
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deletePhoneNumber(Long id) {
        PhoneNumber phoneNumber = phoneNumberRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        phoneNumberRepository.delete(phoneNumber);
    }
}
