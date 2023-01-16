package com.hubformath.mathhubservice.repositories.config;

import com.hubformath.mathhubservice.models.config.Subject;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
