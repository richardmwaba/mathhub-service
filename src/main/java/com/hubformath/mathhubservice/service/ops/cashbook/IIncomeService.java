package com.hubformath.mathhubservice.service.ops.cashbook;
import java.util.List;

import com.hubformath.mathhubservice.dto.ops.cashbook.IncomeRequestDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Income;

public interface IIncomeService {
    List<Income> getAllIncomes();

    Income createIncome(IncomeRequestDto incomeRequest);

    Income getIncomeById(Long id);

    Income updateIncome(Long id, Income income);

    void deleteIncome(Long id);
}