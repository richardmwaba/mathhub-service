package com.hubformath.mathhubservice.dtos.config;

import com.hubformath.mathhubservice.dtos.ops.cashbook.LiabilityDto;

public class LiabilityTypeDto {
    private Long id;

    private String typeName;

    private String typeDescription;

    private LiabilityDto liability;

    public Long getId() {
        return id;
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

    public LiabilityDto getLiability() {
        return liability;
    }

    public void setLiability(LiabilityDto liability) {
        this.liability = liability;
    }
}
