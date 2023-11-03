package com.hubformath.mathhubservice.repository.sis;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.sis.PhoneNumber;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, UUID> {
    
}
