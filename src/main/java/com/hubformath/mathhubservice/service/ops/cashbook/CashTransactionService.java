package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.CashTransaction;
import com.hubformath.mathhubservice.repository.ops.cashbook.CashTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
                                        Optional.ofNullable(transactionRequest.getPaymentMethod())
                                                .ifPresent(transaction::setPaymentMethod);
                                        Optional.ofNullable(transactionRequest.getNarration())
                                                .ifPresent(transaction::setNarration);
                                        Optional.ofNullable(transactionRequest.getTransactionType())
                                                .ifPresent(transaction::setTransactionType);
                                        Optional.ofNullable(transactionRequest.getAmount())
                                                .ifPresent(transaction::setAmount);
                                        Optional.ofNullable(transactionRequest.getTransactionNumber())
                                                .ifPresent(transaction::setTransactionNumber);
                                        Optional.ofNullable(transactionRequest.getTransactionDateTime())
                                                .ifPresent(transaction::setTransactionDateTime);
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
