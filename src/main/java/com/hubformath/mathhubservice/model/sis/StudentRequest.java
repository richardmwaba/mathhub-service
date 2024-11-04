package com.hubformath.mathhubservice.model.sis;

import java.time.LocalDate;
import java.util.List;

public record StudentRequest(String examBoardId,
                             String gradeId,
                             String firstName,
                             String middleName,
                             String lastName,
                             Gender gender,
                             String email,
                             List<Parent> parents,
                             List<Address> addresses,
                             PhoneNumber phoneNumber,
                             LocalDate dateOfBirth) {
}
