package com.hubformath.mathhubservice.service.sis;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.sis.PhoneNumber;
import com.hubformath.mathhubservice.repository.sis.PhoneNumberRepository;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;

    private final String notFoundItemName;
    
    public PhoneNumberService(final PhoneNumberRepository phoneNumberRepository) {
        super();
        this.phoneNumberRepository = phoneNumberRepository;
        this.notFoundItemName = "phoneNumber";
    }

    public List<PhoneNumber> getAllPhoneNumbers() {
        return phoneNumberRepository.findAll();
    }

    public PhoneNumber getPhoneNumberById(final Long id) {
        Optional<PhoneNumber> phoneNumber = phoneNumberRepository.findById(id);

        if(phoneNumber.isPresent()){
            return phoneNumber.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    public PhoneNumber createPhoneNumber(final PhoneNumber phoneNumberRequest) {
        return phoneNumberRepository.save(phoneNumberRequest);
    }

    public PhoneNumber updatePhoneNumber(final Long id, final PhoneNumber phoneNumberRequest) {
        return phoneNumberRepository.findById(id) 
                .map(phoneNumber -> {
                    phoneNumber.setType(phoneNumberRequest.getType());
                    phoneNumber.setCountryCode(phoneNumberRequest.getCountryCode());
                    phoneNumber.setNumber(phoneNumberRequest.getNumber());
                    return phoneNumberRepository.save(phoneNumber);
                }) 
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    public void deletePhoneNumber(final Long id) {
        PhoneNumber phoneNumber = phoneNumberRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        phoneNumberRepository.delete(phoneNumber);
    }
}
