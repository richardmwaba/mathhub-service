package com.hubformath.mathhubservice.models.config;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.hubformath.mathhubservice.models.ops.cashbook.Income;

@Entity
public class IncomeType {
    private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;

    private String typeName;

    private String typeDescription;

    @OneToOne(mappedBy = "incomeType")
    private Income income;

    public IncomeType() {}

    public IncomeType(String typeName, String typeDescription) {
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

    public Income getIncome() {
        return income;
    }

    public void setIncome(Income income) {
        this.income = income;
    }    

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof IncomeType))
            return false;
        IncomeType incomeType = (IncomeType) o;
        return Objects.equals(this.id, incomeType.id) && Objects.equals(this.typeName, incomeType.typeName)
            && Objects.equals(this.typeDescription, incomeType.typeDescription)
            && Objects.equals(this.income, incomeType.income);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.typeName, this.typeDescription, this.income);
    }

    @Override
    public String toString() {
        return "IncomeType{" + "id=" + this.id + ", typeName='" + this.typeName + "'" + 
            ", typeDescription='" + this.typeDescription + ", income='" + this.income + "'}";
    }
}
