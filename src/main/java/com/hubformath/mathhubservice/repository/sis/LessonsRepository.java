package com.hubformath.mathhubservice.repository.sis;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.sis.Lessons;

public interface LessonsRepository extends JpaRepository<Lessons, Long> {

}