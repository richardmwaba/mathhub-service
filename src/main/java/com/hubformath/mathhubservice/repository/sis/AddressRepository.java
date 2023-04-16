package com.hubformath.mathhubservice.repository.sis;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.sis.Address;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    
}
