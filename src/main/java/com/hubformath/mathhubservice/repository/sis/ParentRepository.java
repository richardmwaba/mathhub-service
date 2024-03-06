package com.hubformath.mathhubservice.repository.sis;

import com.hubformath.mathhubservice.model.sis.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent, String> {

}
