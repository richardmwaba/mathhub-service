package com.hubformath.mathhubservice.util;

import com.hubformath.mathhubservice.model.ops.cashbook.PaymentStatus;
import com.hubformath.mathhubservice.model.sis.EnrolledClass;
import com.hubformath.mathhubservice.model.sis.EnrolledClassStatus;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.sis.StudentFinancialSummary;

import java.time.LocalDate;

public class StudentUtil {

    private StudentUtil() {
        throw new IllegalStateException("StudentUtil cannot be initialized because it is a utility class");
    }

    public static StudentFinancialSummary computeStudentFinancialSummary(Student student) {
        double amountOwing = student.getEnrolledClasses()
                                    .stream()
                                    .filter(aClass -> aClass.getEnrolmentStatus()
                                                            .equals(EnrolledClassStatus.ACTIVE) && aClass.getPaymentStatus() == PaymentStatus.UNPAID)
                                    .map(StudentUtil::computeAmountOwing)
                                    .reduce(Double::sum)
                                    .orElse(0d);
        boolean isStudentOwing = isStudentOwing(student, amountOwing);
        return new StudentFinancialSummary(isStudentOwing, amountOwing, computeDueDate(student));
    }

    private static boolean isStudentOwing(Student student, Double amountOwing) {
        boolean hasUnpaidLessons = student.getEnrolledClasses()
                                          .stream()
                                          .anyMatch(aClass -> aClass.getPaymentStatus() == PaymentStatus.UNPAID);
        return hasUnpaidLessons && amountOwing > 0;
    }

    private static LocalDate computeDueDate(Student student) {
        return student.getEnrolledClasses()
                      .stream()
                      .filter(aClass -> aClass.getPaymentStatus() == PaymentStatus.UNPAID)
                      .map(EnrolledClass::getStartDate)
                      .min(LocalDate::compareTo)
                      .map(date -> date.plusDays(30))
                      .orElse(null);
    }

    private static double computeAmountOwing(EnrolledClass enrolledClass) {
        double totalCostForSessions = enrolledClass.getCostPerSession() * enrolledClass.getOccurrencePerWeek();

        return switch (enrolledClass.getPeriod()) {
            case DAYS -> totalCostForSessions;
            case WEEKS -> totalCostForSessions * enrolledClass.getDuration();
            case MONTHS -> totalCostForSessions * enrolledClass.getDuration() * 4;
        };
    }
}
