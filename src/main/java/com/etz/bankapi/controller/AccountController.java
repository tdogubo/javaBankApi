package com.etz.bankapi.controller;

import com.etz.bankapi.domain.request.CreateAccountRequest;
import com.etz.bankapi.domain.request.CreateDepositRequest;
import com.etz.bankapi.domain.response.AccountResponse;
import com.etz.bankapi.domain.response.AppResponse;
import com.etz.bankapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/users/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/new")
    public ResponseEntity<AppResponse<AccountResponse>> createAccount(@RequestParam("userId") Long userId, @RequestBody CreateAccountRequest createAccountRequest) {
        return accountService.createAccount(userId, createAccountRequest);
    }

    @PutMapping("/make_deposit")
    public void creditAccount(@RequestBody CreateDepositRequest createDepositRequest) {
        accountService.makeDeposit(createDepositRequest);
    }

    @PutMapping("/make_transfer")
    public void makeTransfer(@RequestBody CreateDepositRequest createDepositRequest) {
        accountService.makeTransfer(createDepositRequest);
    }
}