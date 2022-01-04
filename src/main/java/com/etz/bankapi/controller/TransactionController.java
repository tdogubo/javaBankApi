package com.etz.bankapi.controller;

import com.etz.bankapi.domain.response.AppResponse;
import com.etz.bankapi.domain.response.TransactionResponse;
import com.etz.bankapi.model.Transactions;
import com.etz.bankapi.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping(path = "/transactions/{id}")
    public ResponseEntity<AppResponse<TransactionResponse>> getTransactions(@PathVariable("id") String transactionId) {
        return transactionService.getTransaction(transactionId);
    }

    @GetMapping(path = "/{accountNumber}/transactions/")
    public Page<Transactions> getAllTransactions(@PathVariable("accountNumber") Long accountNumber,
                                                 @RequestParam(required = false, defaultValue = "0") int pageNumber) {
        return transactionService.allTransactions(accountNumber, pageNumber);
    }
}
