package com.hubformath.mathhubservice.repository.sis;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.sis.Parent;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    
}
