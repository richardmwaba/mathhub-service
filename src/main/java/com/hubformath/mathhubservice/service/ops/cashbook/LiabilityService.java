package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.Liability;
import com.hubformath.mathhubservice.model.ops.cashbook.LiabilityRequest;
import com.hubformath.mathhubservice.model.systemconfig.LiabilityType;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.repository.ops.cashbook.LiabilityRepository;
import com.hubformath.mathhubservice.service.systemconfig.LiabilityTypeService;
import com.hubformath.mathhubservice.service.systemconfig.PaymentMethodService;
import com.hubformath.mathhubservice.service.systemconfig.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LiabilityService {

    private final LiabilityRepository liabilityRepository;

    private final LiabilityTypeService liabilityTypeService;

    private final PaymentMethodService paymentMethodService;

    private final UsersService usersService;

    @Autowired
    public LiabilityService(LiabilityRepository liabilityRepository,
                            LiabilityTypeService liabilityTypeService,
                            PaymentMethodService paymentMethodService,
                            UsersService usersService) {
        this.liabilityRepository = liabilityRepository;
        this.liabilityTypeService = liabilityTypeService;
        this.paymentMethodService = paymentMethodService;
        this.usersService = usersService;
    }

    public List<Liability> getAllLiabilities() {
        return liabilityRepository.findAll();
    }

    public Liability getLiabilityById(String liabilityId) {
        return liabilityRepository.findById(liabilityId).orElseThrow();
    }

    public Liability createLiability(LiabilityRequest liabilityRequest) {
        LiabilityType liabilityType = liabilityTypeService.getLiabilityTypeById(liabilityRequest.liabilityTypeId());
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(liabilityRequest.paymentMethodId());

        Liability newLiability = new Liability(liabilityType,
                                               paymentMethod,
                                               liabilityRequest.amount(),
                                               liabilityRequest.narration(),
                                               usersService.getLoggedInUser());
        return liabilityRepository.save(newLiability);
    }

    public Liability updateLiability(String liabilityId, LiabilityRequest liabilityRequest) {
        return liabilityRepository.findById(liabilityId)
                                  .map(liability -> {
                                      Optional.ofNullable(liabilityRequest.paymentMethodId())
                                              .ifPresent(paymentMethodId -> {
                                                  PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(
                                                          paymentMethodId);
                                                  liability.setPaymentMethod(paymentMethod);
                                              });
                                      Optional.ofNullable(liabilityRequest.liabilityTypeId())
                                              .ifPresent(liabilityTypeId -> {
                                                  LiabilityType liabilityType = liabilityTypeService.getLiabilityTypeById(
                                                          liabilityTypeId);
                                                  liability.setType(liabilityType);
                                              });
                                      Optional.ofNullable(liabilityRequest.amount()).ifPresent(liability::setAmount);
                                      Optional.ofNullable(liabilityRequest.narration())
                                              .ifPresent(liability::setNarration);
                                      return liabilityRepository.save(liability);
                                  })
                                  .orElseThrow();
    }

    public void deleteLiability(String liabilityId) {
        Liability liability = liabilityRepository.findById(liabilityId)
                                                 .orElseThrow();

        liabilityRepository.delete(liability);
    }
}