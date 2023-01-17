package com.hubformath.mathhubservice.services.ops.cashbook;
import java.util.List;

import com.hubformath.mathhubservice.dtos.ops.cashbook.IncomeRequestDto;
import com.hubformath.mathhubservice.models.ops.cashbook.Income;

public interface IIncomeService {
    List<Income> getAllIncomes();

    Income createIncome(IncomeRequestDto incomeRequest);

    Income getIncomeById(Long id);

    Income updateIncome(Long id, Income income);

    void deleteIncome(Long id);
}