package com.hubformath.mathhubservice.controllers.ops.cashbook;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hubformath.mathhubservice.assemblers.ops.cashbook.TransactionModelAssembler;
import com.hubformath.mathhubservice.dtos.ops.cashbook.TransactionDto;
import com.hubformath.mathhubservice.dtos.ops.cashbook.TransactionRequestDto;
import com.hubformath.mathhubservice.models.ops.cashbook.Transaction;
import com.hubformath.mathhubservice.services.ops.cashbook.ITransactionService;


@RestController
@RequestMapping(path="/api/v1/ops")
public class TransactionController {
    @Autowired
    private ModelMapper modelMapper;    
    
    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private TransactionModelAssembler transactionModelAssembler;

    public TransactionController() {
        super();
    }

    @GetMapping("/transactions")
    public ResponseEntity<CollectionModel<EntityModel<TransactionDto>>> getAllTransactions() {
        List<TransactionDto> transactions = transactionService.getAllTransactions().stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDto.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<TransactionDto>> transactionCollectionModel = transactionModelAssembler
                .toCollectionModel(transactions);

        return ResponseEntity.ok().body(transactionCollectionModel);
    }

    @PostMapping("/transactions")
    public ResponseEntity<EntityModel<TransactionDto>> newTransaction(
            @RequestBody TransactionRequestDto transactionRequestDto) {
        Transaction newTransaction = transactionService.createTransaction(transactionRequestDto);

        EntityModel<TransactionDto> transactionEntityModel = transactionModelAssembler
                .toModel(modelMapper.map(newTransaction, TransactionDto.class));

        return ResponseEntity.created(transactionEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(transactionEntityModel);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<EntityModel<TransactionDto>> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);

        EntityModel<TransactionDto> transactionEntityModel = transactionModelAssembler
                .toModel(modelMapper.map(transaction, TransactionDto.class));

        return ResponseEntity.ok().body(transactionEntityModel);
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<EntityModel<TransactionDto>> replaceTransaction(
            @RequestBody TransactionDto transactionDto,
            @PathVariable Long id) {
        Transaction transactionRequest = modelMapper.map(transactionDto, Transaction.class);
        Transaction updatedTransaction = transactionService.updateTransaction(id, transactionRequest);

        EntityModel<TransactionDto> transactionEntityModel = transactionModelAssembler
                .toModel(modelMapper.map(updatedTransaction, TransactionDto.class));

        return ResponseEntity.ok().body(transactionEntityModel);

    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);

        return ResponseEntity.ok().body("Transaction deleted successfully");
    }
}
