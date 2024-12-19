package com.hubformath.mathhubservice.model.sis;

import java.time.LocalDate;

public record EnrolledClassRequest(int occurrence,
                                   String subjectId,
                                   LocalDate startDate,
                                   int duration,
                                   LessonPeriod period,
                                   SessionType sessionType) {
}
