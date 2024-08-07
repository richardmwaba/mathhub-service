package com.hubformath.mathhubservice.repository.sis;

import com.hubformath.mathhubservice.model.sis.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, String> {

}