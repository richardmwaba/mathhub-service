package com.hubformath.mathhubservice.dtos.config;

import com.hubformath.mathhubservice.dtos.ops.cashbook.AssetDto;
import com.hubformath.mathhubservice.dtos.ops.cashbook.EquityDto;
import com.hubformath.mathhubservice.dtos.ops.cashbook.ExpenseDto;
import com.hubformath.mathhubservice.dtos.ops.cashbook.IncomeDto;
import com.hubformath.mathhubservice.dtos.ops.cashbook.LiabilityDto;
import com.hubformath.mathhubservice.dtos.ops.cashbook.TransactionDto;

public class PaymentMethodDto {
    private Long id;

    private String typeName;

    private String typeDescription;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
