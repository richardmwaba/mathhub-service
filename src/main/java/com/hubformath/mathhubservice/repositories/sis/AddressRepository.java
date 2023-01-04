package com.hubformath.mathhubservice.repositories.sis;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.models.sis.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
    
}
