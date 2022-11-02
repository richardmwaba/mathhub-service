package com.hubformath.mathhubservice.models.config;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.hubformath.mathhubservice.models.ops.cashbook.Expense;

@Entity
public class ExpenseType {
    private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;

    private String typeName;

    private String typeDescription;

    @OneToOne(mappedBy = "expenseType")
    private Expense expense;

    public ExpenseType() {}

    public ExpenseType(String typeName, String typeDescription) {
        this.typeName = typeName;
        this.typeDescription = typeDescription;
    }

    public Long getId() {
        return this.id;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDescription() {
        return this.typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ExpenseType))
            return false;
        ExpenseType expenseType = (ExpenseType) o;
        return Objects.equals(this.id, expenseType.id) && Objects.equals(this.typeName, expenseType.typeName)
            && Objects.equals(this.typeDescription, expenseType.typeDescription)
            && Objects.equals(this.expense, expenseType.expense);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.typeName, this.typeDescription);
    }

    @Override
    public String toString() {
        return "IncomeType{" + "id=" + this.id + ", typeName='" + this.typeName + "'" + 
            ", typeDescription='" + this.typeDescription + "'}";
    }

}
