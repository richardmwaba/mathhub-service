package com.hubformath.mathhubservice.model.ops.cashbook;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ExpenseStatus {

    APPROVED("Approved"),
    CREATED("Created"),
    CASHED("Cashed"),
    RETIRED("Retired"),
    REJECTED("Rejected");

    private final String status;

    ExpenseStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }

    public static ExpenseStatus fromStatus(String status) {
        for (ExpenseStatus expenseStatus : ExpenseStatus.values()) {
            if (expenseStatus.status.equalsIgnoreCase(status)) {
                return expenseStatus;
            }
        }
        throw new IllegalArgumentException("No constant with status " + status + " found");
    }

    @Override
    public String toString() {
        return getStatus();
    }
}
