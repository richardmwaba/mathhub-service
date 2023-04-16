package com.hubformath.mathhubservice.dto.sis;

import com.hubformath.mathhubservice.model.sis.PhoneNumberType;

import java.util.UUID;

public class PhoneNumberDto {
    private UUID phoneNumberId;

    private PhoneNumberType type;

    private String countryCode;

    private String number;

    public UUID getPhoneNumberId() {
        return phoneNumberId;
    }

    public void setPhoneNumberId(UUID phoneNumberId) {
        this.phoneNumberId = phoneNumberId;
    }

    public PhoneNumberType getType() {
        return type;
    }

    public void setType(PhoneNumberType type) {
        this.type = type;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
