package com.hubformath.mathhubservice.model.sis;

import java.time.LocalDate;

public record LessonRequest(int occurrence,
                            String subjectId,
                            LocalDate lessonStartDate,
                            int lessonDuration,
                            LessonPeriod lessonPeriod,
                            SessionType sessionType) {
}
