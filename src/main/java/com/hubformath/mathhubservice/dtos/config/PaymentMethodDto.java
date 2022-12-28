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

    private TransactionDto transaction;

    private LiabilityDto liability;

    private IncomeDto income;

    private ExpenseDto expense;

    private EquityDto equity;

    private AssetDto asset;

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

    public TransactionDto getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionDto transaction) {
        this.transaction = transaction;
    }

    public LiabilityDto getLiability() {
        return liability;
    }

    public void setLiability(LiabilityDto liability) {
        this.liability = liability;
    }

    public IncomeDto getIncome() {
        return income;
    }

    public void setIncome(IncomeDto income) {
        this.income = income;
    }

    public ExpenseDto getExpense() {
        return expense;
    }

    public void setExpense(ExpenseDto expense) {
        this.expense = expense;
    }

    public EquityDto getEquity() {
        return equity;
    }

    public void setEquity(EquityDto equity) {
        this.equity = equity;
    }

    public AssetDto getAsset() {
        return asset;
    }

    public void setAsset(AssetDto asset) {
        this.asset = asset;
    }
}
