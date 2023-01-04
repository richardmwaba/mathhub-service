package com.hubformath.mathhubservice.services.ops.cashbook;

import java.util.List;

import com.hubformath.mathhubservice.models.ops.cashbook.Liability;
public interface ILiabilityService {
    List<Liability> getAllLiabilities();

    Liability createLiability(Liability liability);

    Liability getLiabilityById(Long id);

    Liability updateLiability(Long id, Liability liability);

    void deleteLiability(Long id);
}
