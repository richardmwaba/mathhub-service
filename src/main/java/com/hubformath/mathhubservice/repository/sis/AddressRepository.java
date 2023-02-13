package com.hubformath.mathhubservice.repository.sis;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.sis.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
    
}
