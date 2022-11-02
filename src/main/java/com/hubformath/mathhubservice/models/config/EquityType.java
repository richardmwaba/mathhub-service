package com.hubformath.mathhubservice.models.config;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.hubformath.mathhubservice.models.ops.cashbook.Equity;;

@Entity
public class EquityType {
    private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;

    private String typeName;

    private String typeDescription;

    @OneToOne(mappedBy = "equityType")
    private Equity equity;

    public EquityType() {}

    public EquityType(String typeName, String typeDescription) {
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

    public Equity getEquity() {
        return equity;
    }

    public void setEquity(Equity equity) {
        this.equity = equity;
    }    

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof EquityType))
            return false;
        EquityType equityType = (EquityType) o;
        return Objects.equals(this.id, equityType.id) && Objects.equals(this.typeName, equityType.typeName)
            && Objects.equals(this.typeDescription, equityType.typeDescription)
            && Objects.equals(this.equity, equityType.equity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.typeName, this.typeDescription, this.equity);
    }

    @Override
    public String toString() {
        return "EquityType{" + "id=" + this.id + ", typeName='" + this.typeName + "'" + 
            ", typeDescription='" + this.typeDescription + ", equity='" + this.equity + "'}";
    }

}
