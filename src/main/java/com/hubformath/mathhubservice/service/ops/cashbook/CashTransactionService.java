package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.CashTransaction;
import com.hubformath.mathhubservice.model.ops.cashbook.CashTransactionRequest;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.repository.ops.cashbook.CashTransactionRepository;
import com.hubformath.mathhubservice.service.systemconfig.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CashTransactionService {

    private final CashTransactionRepository transactionRepository;

    private final PaymentMethodService paymentMethodService;


    @Autowired
    public CashTransactionService(CashTransactionRepository transactionRepository,
                                  PaymentMethodService paymentMethodService) {
        this.transactionRepository = transactionRepository;
        this.paymentMethodService = paymentMethodService;
    }

    public List<CashTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public CashTransaction getTransactionById(String cashTransactionId) {
        return transactionRepository.findById(cashTransactionId).orElseThrow();
    }

    public CashTransaction updateTransaction(String cashTransactionId, CashTransactionRequest transactionRequest) {
        return transactionRepository.findById(cashTransactionId)
                                    .map(transaction -> {
                                        Optional.ofNullable(transactionRequest.paymentMethodId())
                                                .ifPresent(paymentMethodId -> {
                                                    PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(
                                                            paymentMethodId);
                                                    transaction.setPaymentMethod(paymentMethod);
                                                });
                                        Optional.ofNullable(transactionRequest.narration())
                                                .ifPresent(transaction::setNarration);
                                        Optional.ofNullable(transactionRequest.transactionType())
                                                .ifPresent(transaction::setTransactionType);
                                        Optional.ofNullable(transactionRequest.amount())
                                                .ifPresent(transaction::setAmount);
                                        return transactionRepository.save(transaction);
                                    })
                                    .orElseThrow();
    }

    public void deleteTransaction(String cashTransactionId) {
        CashTransaction transaction = transactionRepository.findById(cashTransactionId)
                                                           .orElseThrow();

        transactionRepository.delete(transaction);
    }
}
