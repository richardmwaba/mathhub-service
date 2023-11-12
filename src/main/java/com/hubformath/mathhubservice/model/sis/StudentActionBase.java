package com.hubformath.mathhubservice.model.sis;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonSubTypes({
        @JsonSubTypes.Type(value = StudentActionGetOwing.class, name = "GET_OWING"),
        @JsonSubTypes.Type(value = StudentActionSendInvoice.class, name = "SEND_INVOICE")
})
public abstract class StudentActionBase {
    public enum StudentActionTypeEnum {
        GET_OWING("PRINT_RECEIPT"),

        SEND_INVOICE("SEND_INVOICE");

        private String value;

        StudentActionTypeEnum(final String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static StudentActionTypeEnum fromValue(final String value) {
            for (StudentActionTypeEnum action : StudentActionTypeEnum.values()) {
                if (action.value.equals(value)) {
                    return action;
                }
            }
            throw new IllegalArgumentException("unexpected value '" + value + "'");
        }
    }

    private StudentActionTypeEnum actionType;

    StudentActionBase(StudentActionTypeEnum actionType) {
        this.actionType = actionType;
    }

    public StudentActionTypeEnum getActionType() {
        return actionType;
    }

    public void setActionType(StudentActionTypeEnum actionType) {
        this.actionType = actionType;
    }
}
