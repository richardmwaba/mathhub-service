package com.hubformath.mathhubservice.dto.ops.cashbook;

import com.hubformath.mathhubservice.dto.systemconfig.AssetTypeDto;
import com.hubformath.mathhubservice.dto.systemconfig.PaymentMethodDto;

import java.util.UUID;

public class AssetDto {
    private UUID assetId;

    private PaymentMethodDto paymentMethod;

    private String narration;

    private AssetTypeDto assetType;

    private Double amount;

    public UUID getAssetId() {
        return assetId;
    }

    public void setAssetId(UUID assetId) {
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
