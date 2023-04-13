package com.hubformath.mathhubservice.dto.systemconfig;

import java.util.UUID;

public class SessionTypeDto {
    private UUID sessionTypeId;

    private String typeName;

    private String typeDescription;

    public UUID getSessionTypeId() {
        return sessionTypeId;
    }

    public void setSessionTypeId(UUID sessionTypeId) {
        this.sessionTypeId = sessionTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }
}
