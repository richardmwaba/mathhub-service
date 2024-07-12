package com.hubformath.mathhubservice.repository.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.ClassRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRateRepository extends JpaRepository<ClassRate, String> {
}
