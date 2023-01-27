package com.hubformath.mathhubservice.repositories.config;

import com.hubformath.mathhubservice.models.config.ExamBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamBoardRepository extends JpaRepository<ExamBoard, Long> {
}
