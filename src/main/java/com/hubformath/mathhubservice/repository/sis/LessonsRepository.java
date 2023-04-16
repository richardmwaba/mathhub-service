package com.hubformath.mathhubservice.repository.sis;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.sis.Lesson;

import java.util.UUID;

public interface LessonsRepository extends JpaRepository<Lesson, UUID> {

}