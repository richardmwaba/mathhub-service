package com.hubformath.mathhubservice.service.ops.cashbook;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.ops.cashbook.CashTransaction;
import com.hubformath.mathhubservice.repository.ops.cashbook.CashTransactionRepository;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class CashTransactionService {

    private final CashTransactionRepository transactionRepository;

    private final String notFoundItemName;

    @Autowired
    public CashTransactionService(final CashTransactionRepository transactionRepository) {
        super();
        this.transactionRepository = transactionRepository;
        this.notFoundItemName = "transaction";
    }

    public List<CashTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public CashTransaction getTransactionById(Long id) {
        Optional<CashTransaction> transaction = transactionRepository.findById(id);

        if(transaction.isPresent()){
            return transaction.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
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
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    public void deleteTransaction(Long id) {
        CashTransaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        transactionRepository.delete(transaction);
    }
}
