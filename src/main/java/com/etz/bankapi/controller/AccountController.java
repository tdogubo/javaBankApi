package com.etz.bankapi.controller;

import com.etz.bankapi.domain.request.AccountStatusRequest;
import com.etz.bankapi.domain.request.CreateAccountRequest;
import com.etz.bankapi.domain.request.CreateDepositRequest;
import com.etz.bankapi.domain.request.CreateTransferRequest;
import com.etz.bankapi.domain.response.AccountStatusResponse;
import com.etz.bankapi.domain.response.AppResponse;
import com.etz.bankapi.domain.response.CreateAccountResponse;
import com.etz.bankapi.domain.response.TransactionResponse;
import com.etz.bankapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AppResponse<CreateAccountResponse>> createNewAccount(@RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request);
    }

    @PostMapping("/deposit")
    public ResponseEntity<AppResponse<TransactionResponse>> creditAccount(@RequestBody CreateDepositRequest request) {
        return accountService.makeDeposit(request);
    }

    @PostMapping("/transfer")
    public ResponseEntity<AppResponse<TransactionResponse>> makeTransfer(@Valid @RequestBody CreateTransferRequest request) {
        return accountService.makeTransfer(request);
    }

    @PutMapping("/status")
    public ResponseEntity<AppResponse<AccountStatusResponse>> accountStatus(@RequestBody AccountStatusRequest request) {
        return accountService.accountStatus(request);
    }
}