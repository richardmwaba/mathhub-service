package com.hubformath.mathhubservice.repositories.sis;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.models.sis.Parent;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    
}
