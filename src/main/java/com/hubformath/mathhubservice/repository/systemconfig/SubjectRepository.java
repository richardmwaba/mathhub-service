package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.Subject;

import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {

}
