package com.hubformath.mathhubservice.service.sis;

import com.hubformath.mathhubservice.model.sis.PhoneNumber;
import com.hubformath.mathhubservice.repository.sis.PhoneNumberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;

    public PhoneNumberService(final PhoneNumberRepository phoneNumberRepository) {
        super();
        this.phoneNumberRepository = phoneNumberRepository;
    }

    public List<PhoneNumber> getAllPhoneNumbers() {
        return phoneNumberRepository.findAll();
    }

    public PhoneNumber getPhoneNumberById(final UUID phoneNumberId) {
        return phoneNumberRepository.findById(phoneNumberId).orElseThrow();
    }

    public PhoneNumber createPhoneNumber(final PhoneNumber phoneNumberRequest) {
        return phoneNumberRepository.save(phoneNumberRequest);
    }

    public PhoneNumber updatePhoneNumber(final UUID phoneNumberId, final PhoneNumber phoneNumberRequest) {
        return phoneNumberRepository.findById(phoneNumberId)
                .map(phoneNumber -> {
                    Optional.ofNullable(phoneNumberRequest.getType()).ifPresent(phoneNumber::setType);
                    Optional.ofNullable(phoneNumberRequest.getCountryCode()).ifPresent(phoneNumber::setCountryCode);
                    Optional.ofNullable(phoneNumberRequest.getNumber()).ifPresent(phoneNumber::setNumber);
                    return phoneNumberRepository.save(phoneNumber);
                }) 
                .orElseThrow();
    }

    public void deletePhoneNumber(final UUID phoneNumberId) {
        PhoneNumber phoneNumber = phoneNumberRepository.findById(phoneNumberId)
                .orElseThrow();

        phoneNumberRepository.delete(phoneNumber);
    }
}
