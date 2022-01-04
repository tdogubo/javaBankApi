package com.etz.bankapi.service;

import com.etz.bankapi.domain.response.AppResponse;
import com.etz.bankapi.domain.response.TransactionResponse;
import com.etz.bankapi.model.Transactions;
import com.etz.bankapi.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;


    public ResponseEntity<AppResponse<TransactionResponse>> getTransaction(String transactionId) {
        Optional<Transactions> transaction = transactionRepository.findById(transactionId);
        if (transaction.isPresent()) {
            TransactionResponse response = new TransactionResponse();
            response.setAccountNumber(transaction.get().getAccountNumber());
            response.setAmount(transaction.get().getAmount());
            response.setTransactionId(transaction.get().getTransactionId());
            response.setTransactionType(transaction.get().getTransactionType());
            response.setTransactionDate(transaction.get().getTransactionDate());
            return new ResponseEntity<>(new AppResponse<>(true, response), HttpStatus.OK);

        }
        return new ResponseEntity<>(new AppResponse<>(false, "Transaction not found"), HttpStatus.NOT_FOUND);
    }

    public Page<Transactions> allTransactions(Long accountNumber, Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 10);

        return transactionRepository.findAllByAccountNumber(accountNumber, page);


    }
}
