package com.hubformath.mathhubservice.service.ops.cashbook.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.ops.cashbook.CashTransaction;
import com.hubformath.mathhubservice.repository.ops.cashbook.CashTransactionRepository;
import com.hubformath.mathhubservice.service.ops.cashbook.ICashTransactionService;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class CashTransactionServiceImpl implements ICashTransactionService{

    private final CashTransactionRepository transactionRepository;

    private final String notFoundItemName;

    public CashTransactionServiceImpl(CashTransactionRepository transactionRepository) {
        super();
        this.transactionRepository = transactionRepository;
        this.notFoundItemName = "transaction";
    }

    @Override
    public List<CashTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public CashTransaction getTransactionById(Long id) {
        Optional<CashTransaction> transaction = transactionRepository.findById(id);

        if(transaction.isPresent()){
            return transaction.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public CashTransaction createTransaction(CashTransaction cashTransaction) {
        return transactionRepository.save(cashTransaction);
    }

    @Override
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
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteTransaction(Long id) {
        CashTransaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        transactionRepository.delete(transaction);
    }
}