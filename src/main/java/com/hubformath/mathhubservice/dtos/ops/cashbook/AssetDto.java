package com.hubformath.mathhubservice.dtos.ops.cashbook;

import com.hubformath.mathhubservice.dtos.config.AssetTypeDto;
import com.hubformath.mathhubservice.dtos.config.PaymentMethodDto;

public class AssetDto {
    private Long id;

    private PaymentMethodDto paymentMethod;

    private String narration;

    private AssetTypeDto assetType;

    private Double amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
