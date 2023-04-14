package com.hubformath.mathhubservice.repository.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.LessonRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LessonRateRepository extends JpaRepository<LessonRate, UUID> {
}
