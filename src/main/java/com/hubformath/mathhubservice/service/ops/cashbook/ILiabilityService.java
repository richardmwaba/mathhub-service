package com.hubformath.mathhubservice.service.ops.cashbook;

import java.util.List;

import com.hubformath.mathhubservice.model.ops.cashbook.Liability;
public interface ILiabilityService {
    List<Liability> getAllLiabilities();

    Liability createLiability(Liability liability);

    Liability getLiabilityById(Long id);

    Liability updateLiability(Long id, Liability liability);

    void deleteLiability(Long id);
}
