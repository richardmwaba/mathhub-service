package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.model.auth.User;
import com.hubformath.mathhubservice.model.auth.UserAuthDetails;
import com.hubformath.mathhubservice.model.ops.cashbook.Asset;
import com.hubformath.mathhubservice.model.ops.cashbook.AssetRequest;
import com.hubformath.mathhubservice.model.systemconfig.AssetType;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.repository.ops.cashbook.AssetRepository;
import com.hubformath.mathhubservice.service.systemconfig.AssetTypeService;
import com.hubformath.mathhubservice.service.systemconfig.PaymentMethodService;
import com.hubformath.mathhubservice.service.systemconfig.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetService {

    private final AssetRepository assetRepository;

    private final PaymentMethodService paymentMethodService;

    private final AssetTypeService assetTypeService;

    private final UsersService usersService;

    @Autowired
    public AssetService(final AssetRepository assetRepository,
                        final PaymentMethodService paymentMethodService,
                        final AssetTypeService assetTypeService,
                        final UsersService usersService) {
        this.assetRepository = assetRepository;
        this.paymentMethodService = paymentMethodService;
        this.assetTypeService = assetTypeService;
        this.usersService = usersService;
    }

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Asset getAssetById(String assetId) {
        return assetRepository.findById(assetId).orElseThrow();
    }

    public Asset createAsset(AssetRequest assetRequest) {
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(assetRequest.paymentMethodId());
        AssetType assetType = assetTypeService.getAssetTypeById(assetRequest.assetTypeId());
        UserAuthDetails userDetails = (UserAuthDetails) SecurityContextHolder.getContext()
                                                                             .getAuthentication()
                                                                             .getPrincipal();
        User createdBy = usersService.getUserById(userDetails.getUserId());
        Asset newAsset = new Asset(assetRequest.narration(), assetRequest.amount(), paymentMethod, assetType);
        newAsset.setCreatedBy(createdBy);

        return assetRepository.save(newAsset);
    }

    public Asset updateAsset(String assetId, AssetRequest assetRequest) {
        return assetRepository.findById(assetId)
                              .map(updatedAsset -> {
                                  Optional.ofNullable(assetRequest.paymentMethodId()).ifPresent(paymentMethodId -> {
                                      PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(paymentMethodId);
                                      updatedAsset.setPaymentMethod(paymentMethod);
                                  });
                                  Optional.ofNullable(assetRequest.narration()).ifPresent(updatedAsset::setNarration);
                                  Optional.ofNullable(assetRequest.assetTypeId()).ifPresent(assetTypeId -> {
                                      AssetType assetType = assetTypeService.getAssetTypeById(assetTypeId);
                                      updatedAsset.setAssetType(assetType);
                                  });
                                  Optional.ofNullable(assetRequest.amount()).ifPresent(updatedAsset::setAmount);
                                  return assetRepository.save(updatedAsset);
                              })
                              .orElseThrow();
    }

    public void deleteAsset(String assetId) {
        Asset asset = assetRepository.findById(assetId).orElseThrow();

        assetRepository.delete(asset);
    }
}
