package com.hubformath.mathhubservice.service.ops.cashbook;
import java.util.List;

import com.hubformath.mathhubservice.dto.ops.cashbook.CashTransactionRequestDto;
import com.hubformath.mathhubservice.model.ops.cashbook.CashTransaction;

public interface ICashTransactionService {
    List<CashTransaction> getAllTransactions();

    CashTransaction createTransaction(CashTransactionRequestDto transactionRequest);

    CashTransaction getTransactionById(Long id);

    CashTransaction updateTransaction(Long id, CashTransaction transaction);

    void deleteTransaction(Long id);
}
