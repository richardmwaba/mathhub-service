package com.hubformath.mathhubservice.service.ops.cashbook.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.dto.ops.cashbook.TransactionRequestDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Transaction;
import com.hubformath.mathhubservice.model.ops.cashbook.TransactionType;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.repository.ops.cashbook.TransactionRepository;
import com.hubformath.mathhubservice.service.ops.cashbook.ITransactionService;
import com.hubformath.mathhubservice.service.systemconfig.IPaymentMethodService;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class TransactionServiceImpl implements ITransactionService{
    
    @Autowired
    private IPaymentMethodService paymentMethodService;

    private final TransactionRepository transactionRepository;

    private final String notFoundItemName;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        super();
        this.transactionRepository = transactionRepository;
        this.notFoundItemName = "transaction";
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransactionById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);

        if(transaction.isPresent()){
            return transaction.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public Transaction createTransaction(TransactionRequestDto transactionRequest) {
        final Long paymentMethodId = transactionRequest.getPaymentMethodId();
        final TransactionType transactionType = transactionRequest.getTransactionType();
        final String narration = transactionRequest.getNarration();
        final Double amount = transactionRequest.getAmount();
        final LocalDateTime tranDateTime = transactionRequest.getTransactionDateTime();
        final String transactionNumber = UUID.randomUUID().toString().substring(0, 11).toUpperCase();

        final PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(paymentMethodId);

        // To do: Replace null transacted by with actual logged in user
        final Transaction newTransaction = new Transaction(transactionNumber, transactionType, narration, amount, tranDateTime, null);
        newTransaction.setPaymentMethod(paymentMethod);

        return transactionRepository.save(newTransaction);
    }

    @Override
    public Transaction updateTransaction(Long id, Transaction transactionRequest) {
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
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        transactionRepository.delete(transaction);
    }
}
