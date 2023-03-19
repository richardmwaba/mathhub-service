package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.CashTransaction;
import com.hubformath.mathhubservice.repository.ops.cashbook.CashTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public CashTransaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElseThrow();
    }

    public CashTransaction createTransaction(CashTransaction cashTransaction) {
        return transactionRepository.save(cashTransaction);
    }

    public CashTransaction updateTransaction(Long id, CashTransaction transactionRequest) {
        return transactionRepository.findById(id)
                .map(transaction -> {
                    transaction.setPaymentMethod(transactionRequest.getPaymentMethod());
                    transaction.setNarration(transactionRequest.getNarration());
                    transaction.setTransactionType(transactionRequest.getTransactionType());
                    transaction.setAmount(transactionRequest.getAmount());
                    transaction.setTransactionNumber(transactionRequest.getTransactionNumber());
                    transaction.setTransactionDate(transactionRequest.getTransactionDateTime());
                    return transactionRepository.save(transaction);
                })
                .orElseThrow();
    }

    public void deleteTransaction(Long id) {
        CashTransaction transaction = transactionRepository.findById(id)
                .orElseThrow();

        transactionRepository.delete(transaction);
    }
}
