package com.hubformath.mathhubservice.models.sis;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PhoneNumber {
    private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;
    private PhoneNumberType type;
    private String countryCode;
    private String number;
    
    public PhoneNumber() {
    }

    public PhoneNumber(PhoneNumberType type, String countryCode, String number) {
        this.type = type;
        this.countryCode = countryCode;
        this.number = number;
    }

    public Long getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PhoneNumber))
            return false;
        PhoneNumber phoneNumber = (PhoneNumber) o;
        return Objects.equals(this.id, phoneNumber.id) && Objects.equals(this.type, phoneNumber.type)
            && Objects.equals(this.countryCode, phoneNumber.countryCode)
            && Objects.equals(this.number, phoneNumber.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.type, this.countryCode, this.number);
    }

    @Override
    public String toString() {
        return "PhoneNumber {id=" + this.id + ", type=" + this.type + ", countryCode=" + this.countryCode 
                + ", number=" + this.number + "}";
    }

    
}
