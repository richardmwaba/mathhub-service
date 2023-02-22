package com.hubformath.mathhubservice.dto.sis;

import com.hubformath.mathhubservice.model.sis.PhoneNumberType;

public class PhoneNumberDto {
    private Long id;

    private PhoneNumberType type;

    private String countryCode;

    private String number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
