package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.CashTransaction;
import com.hubformath.mathhubservice.repository.ops.cashbook.CashTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CashTransactionService {

    private final CashTransactionRepository transactionRepository;

    @Autowired
    public CashTransactionService(final CashTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<CashTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public CashTransaction getTransactionById(UUID cashTransactionId) {
        return transactionRepository.findById(cashTransactionId).orElseThrow();
    }

    public CashTransaction createTransaction(CashTransaction cashTransaction) {
        return transactionRepository.save(cashTransaction);
    }

    public CashTransaction updateTransaction(UUID cashTransactionId, CashTransaction transactionRequest) {
        return transactionRepository.findById(cashTransactionId)
                .map(transaction -> {
                    transaction.setPaymentMethod(transactionRequest.getPaymentMethod());
                    transaction.setNarration(transactionRequest.getNarration());
                    transaction.setTransactionType(transactionRequest.getTransactionType());
                    transaction.setAmount(transactionRequest.getAmount());
                    transaction.setTransactionNumber(transactionRequest.getTransactionNumber());
                    transaction.setTransactionDateTime(transactionRequest.getTransactionDateTime());
                    return transactionRepository.save(transaction);
                })
                .orElseThrow();
    }

    public void deleteTransaction(UUID cashTransactionId) {
        CashTransaction transaction = transactionRepository.findById(cashTransactionId)
                .orElseThrow();

        transactionRepository.delete(transaction);
    }
}
