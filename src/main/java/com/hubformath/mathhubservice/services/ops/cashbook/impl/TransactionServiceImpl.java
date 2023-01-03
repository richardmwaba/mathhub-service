package com.hubformath.mathhubservice.services.ops.cashbook.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.models.ops.cashbook.Transaction;
import com.hubformath.mathhubservice.repositories.ops.cashbook.TransactionRepository;
import com.hubformath.mathhubservice.services.ops.cashbook.ITransactionService;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@Service
public class TransactionServiceImpl implements ITransactionService{
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
    public Transaction createTransaction(Transaction transactionRequest) {
        return transactionRepository.save(transactionRequest);
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
                    transaction.setTransactionDate(transactionRequest.getTransactionDate());
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
