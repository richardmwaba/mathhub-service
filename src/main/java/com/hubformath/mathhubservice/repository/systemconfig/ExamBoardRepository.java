package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.ExamBoard;

import java.util.UUID;

public interface ExamBoardRepository extends JpaRepository<ExamBoard, UUID> {
}
