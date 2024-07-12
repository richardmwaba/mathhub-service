package com.hubformath.mathhubservice.repository.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {

    boolean existsByName(String name);
}
