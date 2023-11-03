package com.hubformath.mathhubservice.repository.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.LessonRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LessonRateRepository extends JpaRepository<LessonRate, UUID> {
}
