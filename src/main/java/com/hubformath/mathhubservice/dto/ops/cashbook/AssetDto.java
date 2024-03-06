package com.hubformath.mathhubservice.dto.ops.cashbook;

import com.hubformath.mathhubservice.dto.systemconfig.AssetTypeDto;
import com.hubformath.mathhubservice.dto.systemconfig.PaymentMethodDto;

public class AssetDto {

    private String assetId;

    private PaymentMethodDto paymentMethod;

    private String narration;

    private AssetTypeDto assetType;

    private Double amount;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public PaymentMethodDto getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodDto paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public AssetTypeDto getType() {
        return assetType;
    }

    public void setType(AssetTypeDto assetType) {
        this.assetType = assetType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
