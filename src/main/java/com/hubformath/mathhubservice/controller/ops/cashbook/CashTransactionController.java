package com.hubformath.mathhubservice.controller.ops.cashbook;

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

import com.hubformath.mathhubservice.assembler.ops.cashbook.CashTransactionModelAssembler;
import com.hubformath.mathhubservice.dto.ops.cashbook.CashTransactionDto;
import com.hubformath.mathhubservice.dto.ops.cashbook.CashTransactionRequestDto;
import com.hubformath.mathhubservice.model.ops.cashbook.CashTransaction;
import com.hubformath.mathhubservice.service.ops.cashbook.ICashTransactionService;


@RestController
@RequestMapping(path="/api/v1/ops")
public class CashTransactionController {
    @Autowired
    private ModelMapper modelMapper;    
    
    @Autowired
    private ICashTransactionService transactionService;

    @Autowired
    private CashTransactionModelAssembler transactionModelAssembler;

    public CashTransactionController() {
        super();
    }

    @GetMapping("/transactions")
    public ResponseEntity<CollectionModel<EntityModel<CashTransactionDto>>> getAllTransactions() {
        List<CashTransactionDto> transactions = transactionService.getAllTransactions().stream()
                .map(transaction -> modelMapper.map(transaction, CashTransactionDto.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<CashTransactionDto>> transactionCollectionModel = transactionModelAssembler
                .toCollectionModel(transactions);

        return ResponseEntity.ok().body(transactionCollectionModel);
    }

    @PostMapping("/transactions")
    public ResponseEntity<EntityModel<CashTransactionDto>> newTransaction(
            @RequestBody CashTransactionRequestDto transactionRequestDto) {
        CashTransaction newTransaction = transactionService.createTransaction(transactionRequestDto);

        EntityModel<CashTransactionDto> transactionEntityModel = transactionModelAssembler
                .toModel(modelMapper.map(newTransaction, CashTransactionDto.class));

        return ResponseEntity.created(transactionEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(transactionEntityModel);
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
