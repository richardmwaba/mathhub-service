package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.model.auth.User;
import com.hubformath.mathhubservice.model.ops.cashbook.Equity;
import com.hubformath.mathhubservice.model.ops.cashbook.EquityRequest;
import com.hubformath.mathhubservice.model.systemconfig.EquityType;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.repository.ops.cashbook.EquityRepository;
import com.hubformath.mathhubservice.service.systemconfig.EquityTypeService;
import com.hubformath.mathhubservice.service.systemconfig.PaymentMethodService;
import com.hubformath.mathhubservice.service.systemconfig.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquityService {

    private final EquityRepository equityRepository;

    private final PaymentMethodService paymentMethodService;

    private final EquityTypeService equityTypeService;

    private final UsersService usersService;

    @Autowired
    public EquityService(EquityRepository equityRepository,
                         PaymentMethodService paymentMethodService,
                         EquityTypeService equityTypeService,
                         UsersService usersService) {
        this.equityRepository = equityRepository;
        this.paymentMethodService = paymentMethodService;
        this.equityTypeService = equityTypeService;
        this.usersService = usersService;
    }

    public List<Equity> getAllEquity() {
        return equityRepository.findAll();
    }

    public Equity getEquityById(String equityId) {
        return equityRepository.findById(equityId).orElseThrow();
    }

    public Equity createEquity(EquityRequest equityRequest) {
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(equityRequest.paymentMethodId());
        EquityType equityType = equityTypeService.getEquityTypeById(equityRequest.equityTypeId());
        User createdBy = usersService.getLoggedInUser();
        Equity newEquity = new Equity(paymentMethod,
                                      equityRequest.narration(),
                                      equityType,
                                      equityRequest.amount(),
                                      createdBy);

        return equityRepository.save(newEquity);
    }

    public Equity updateEquity(String equityId, EquityRequest equityRequest) {
        return equityRepository.findById(equityId)
                               .map(equity -> {
                                   Optional.ofNullable(equityRequest.paymentMethodId()).ifPresent(paymentMethodId -> {
                                               PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(
                                                       paymentMethodId);
                                               equity.setPaymentMethod(paymentMethod);
                                           });
                                   Optional.ofNullable(equityRequest.narration()).ifPresent(equity::setNarration);
                                   Optional.ofNullable(equityRequest.equityTypeId()).ifPresent(equityTypeId -> {
                                       EquityType equityType = equityTypeService.getEquityTypeById(equityTypeId);
                                       equity.setEquityType(equityType);
                                   });
                                   Optional.ofNullable(equityRequest.amount()).ifPresent(equity::setAmount);
                                   return equityRepository.save(equity);
                               })
                               .orElseThrow();
    }

    public void deleteEquity(String equityId) {
        Equity equity = equityRepository.findById(equityId)
                                        .orElseThrow();

        equityRepository.delete(equity);
    }
}
