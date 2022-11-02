package com.hubformath.mathhubservice.models.config;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.hubformath.mathhubservice.models.ops.cashbook.Asset;

@Entity
public class AssetType {
    private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;
    
    private String typeName;

    private String typeDescription;

    @OneToOne(mappedBy = "assetType")
    private Asset asset;

    public AssetType() {}

    public AssetType(String typeName, String typeDescription) {
        this.typeName = typeName;
        this.typeDescription = typeDescription;
    }

    public Long getId() {
        return this.id;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AssetType))
            return false;
        AssetType assetType = (AssetType) o;
        return Objects.equals(this.id, assetType.id) && Objects.equals(this.typeName, assetType.typeName)
            && Objects.equals(this.typeDescription, assetType.typeDescription)
            && Objects.equals(this.asset, assetType.asset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.typeName, this.typeDescription);
    }

    @Override
    public String toString() {
        return "UserType{" + "id=" + this.id + ", typeName='" + this.typeName + "'" + 
            ", typeDescription='" + this.typeDescription + ", asset='" + this.asset + "'}";
    }
}
