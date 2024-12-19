package com.hubformath.mathhubservice.repository.sis;

import com.hubformath.mathhubservice.model.sis.EnrolledClass;
import com.hubformath.mathhubservice.model.sis.EnrolledClassStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrolledClassRepository extends JpaRepository<EnrolledClass, String> {

    List<EnrolledClass> findByEnrolmentStatus(EnrolledClassStatus enrolmentStatus);
}