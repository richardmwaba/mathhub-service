package com.hubformath.mathhubservice.repository.sis;

import com.hubformath.mathhubservice.model.sis.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, String> {

}
