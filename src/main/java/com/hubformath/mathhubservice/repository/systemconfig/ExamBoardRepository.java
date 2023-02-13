package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.ExamBoard;

public interface ExamBoardRepository extends JpaRepository<ExamBoard, Long> {
}
