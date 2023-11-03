package com.hubformath.mathhubservice.repository.sis;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.sis.Parent;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParentRepository extends JpaRepository<Parent, UUID> {
    
}
