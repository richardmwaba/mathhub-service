package com.hubformath.mathhubservice.dtos.config;

import com.hubformath.mathhubservice.dtos.ops.cashbook.EquityDto;

public class EquityTypeDto {
    private Long id;

    private String typeName;

    private String typeDescription;

    private EquityDto equity;

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

    public EquityDto getEquity() {
        return equity;
    }

    public void setEquity(EquityDto equity) {
        this.equity = equity;
    }
}
