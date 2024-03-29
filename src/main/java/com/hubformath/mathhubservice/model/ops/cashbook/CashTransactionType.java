package com.hubformath.mathhubservice.model.ops.cashbook;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CashTransactionType {

    CASH_IN("Cash in"),
    CASH_OUT("Cash out");

    private final String description;

    CashTransactionType(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    public static CashTransactionType fromDescription(String description) {
        for (CashTransactionType type : CashTransactionType.values()) {
            if (type.getDescription().equals(description)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
