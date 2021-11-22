package com.etz.bank_api.controller;

import com.etz.bank_api.config.Response;
import com.etz.bank_api.domain.request.AccountRequest;
import com.etz.bank_api.domain.request.DepositRequest;
import com.etz.bank_api.domain.response.AccountResponse;
import com.etz.bank_api.domain.response.UserResponse;
import com.etz.bank_api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/users/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<Response<AccountResponse>>createAccount(@RequestParam("userId") Long userId, @RequestBody AccountRequest accountRequest) {
        return accountService.createAccount(userId, accountRequest);
    }

    @PutMapping("/make_deposit")
    public void creditAccount(@RequestBody DepositRequest depositRequest) {
        accountService.makeDeposit(depositRequest);
    }
    @PutMapping("/make_transfer")
    public void makeTransfer(@RequestBody DepositRequest depositRequest) {
        accountService.makeTransfer(depositRequest);
    }
}