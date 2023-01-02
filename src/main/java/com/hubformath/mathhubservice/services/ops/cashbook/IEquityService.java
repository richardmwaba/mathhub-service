package com.hubformath.mathhubservice.services.ops.cashbook;

import java.util.List;

import com.hubformath.mathhubservice.models.ops.cashbook.Equity;

public interface IEquityService {
    List<Equity> getAllEquity();

    Equity createEquity(Equity equity);

    Equity getEquityById(Long id);

    Equity updateEquity(Long id, Equity equity);

    void deleteEquity(Long id);
}
