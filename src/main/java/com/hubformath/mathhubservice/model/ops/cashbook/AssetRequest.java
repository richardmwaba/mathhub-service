package com.hubformath.mathhubservice.model.ops.cashbook;

public record AssetRequest(String paymentMethodId,
                           String narration,
                           String assetTypeId,
                           Double amount) {
}
