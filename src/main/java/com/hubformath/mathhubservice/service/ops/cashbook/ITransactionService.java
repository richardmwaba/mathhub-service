package com.hubformath.mathhubservice.service.ops.cashbook;
import java.util.List;

import com.hubformath.mathhubservice.dto.ops.cashbook.TransactionRequestDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Transaction;

public interface ITransactionService {
    List<Transaction> getAllTransactions();

    Transaction createTransaction(TransactionRequestDto transactionRequest);

    Transaction getTransactionById(Long id);

    Transaction updateTransaction(Long id, Transaction transaction);

    void deleteTransaction(Long id);
}
