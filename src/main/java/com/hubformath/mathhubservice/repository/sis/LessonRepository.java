package com.hubformath.mathhubservice.repository.sis;

import com.hubformath.mathhubservice.model.sis.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, String> {

}