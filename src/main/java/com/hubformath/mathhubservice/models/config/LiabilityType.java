package com.hubformath.mathhubservice.models.config;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.hubformath.mathhubservice.models.ops.cashbook.Liability;

@Entity
public class LiabilityType {
    private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;

    private String typeName;

    private String typeDescription;

    @OneToOne(mappedBy = "liabilityType")
    private Liability liability;

    public LiabilityType() {}

    public LiabilityType(String typeName, String typeDescription) {
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

    public Liability getLiability() {
        return liability;
    }

    public void setLiability(Liability liability) {
        this.liability = liability;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof LiabilityType))
            return false;
        LiabilityType liabilityType = (LiabilityType) o;
        return Objects.equals(this.id, liabilityType.id) && Objects.equals(this.typeName, liabilityType.typeName)
            && Objects.equals(this.typeDescription, liabilityType.typeDescription)
            && Objects.equals(this.liability, liabilityType.liability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.typeName, this.typeDescription);
    }

    @Override
    public String toString() {
        return "UserType{" + "id=" + this.id + ", typeName='" + this.typeName + "'" + 
            ", typeDescription='" + this.typeDescription + "'}";
    }
}
