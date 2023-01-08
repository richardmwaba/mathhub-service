package com.hubformath.mathhubservice.repositories.sis;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.models.sis.PhoneNumber;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    
}
