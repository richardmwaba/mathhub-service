package com.hubformath.mathhubservice.controller.ops.cashbook;

import java.util.List;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hubformath.mathhubservice.assembler.ops.cashbook.CashTransactionModelAssembler;
import com.hubformath.mathhubservice.dto.ops.cashbook.CashTransactionDto;
import com.hubformath.mathhubservice.model.ops.cashbook.CashTransaction;
import com.hubformath.mathhubservice.service.ops.cashbook.CashTransactionService;


@RestController
@RequestMapping(path="/api/v1/ops")
public class CashTransactionController {
    @Autowired
    private ModelMapper modelMapper;    
    
    @Autowired
    private CashTransactionService transactionService;

    @Autowired
    private CashTransactionModelAssembler transactionModelAssembler;

    public CashTransactionController() {
        super();
    }

    @GetMapping("/transactions")
    public ResponseEntity<CollectionModel<EntityModel<CashTransactionDto>>> getAllTransactions() {
        List<CashTransactionDto> transactions = transactionService.getAllTransactions().stream()
                .map(transaction -> modelMapper.map(transaction, CashTransactionDto.class))
                .toList();

        CollectionModel<EntityModel<CashTransactionDto>> transactionCollectionModel = transactionModelAssembler
                .toCollectionModel(transactions);

        return ResponseEntity.ok().body(transactionCollectionModel);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<EntityModel<CashTransactionDto>> getTransactionById(@PathVariable Long id) {
        CashTransaction transaction = transactionService.getTransactionById(id);

        EntityModel<CashTransactionDto> transactionEntityModel = transactionModelAssembler
                .toModel(modelMapper.map(transaction, CashTransactionDto.class));

        return ResponseEntity.ok().body(transactionEntityModel);
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<EntityModel<CashTransactionDto>> replaceTransaction(
            @RequestBody CashTransactionDto transactionDto,
            @PathVariable Long id) {
        CashTransaction transactionRequest = modelMapper.map(transactionDto, CashTransaction.class);
        CashTransaction updatedTransaction = transactionService.updateTransaction(id, transactionRequest);

        EntityModel<CashTransactionDto> transactionEntityModel = transactionModelAssembler
                .toModel(modelMapper.map(updatedTransaction, CashTransactionDto.class));

        return ResponseEntity.ok().body(transactionEntityModel);

    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);

        return ResponseEntity.ok().body("Transaction deleted successfully");
    }
}
