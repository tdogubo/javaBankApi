package com.etz.bank_api.controller;

import com.etz.bank_api.domain.request.AccountRequest;
import com.etz.bank_api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/users/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    public String createAccount(@RequestParam("userId") Long userId, @RequestBody AccountRequest accountRequest) {
        return accountService.createAccount(userId, accountRequest);
    }

    @PutMapping("/make_deposit")
    public void creditAccount(@RequestBody AccountRequest accountRequest) {
        accountService.makeDeposit(accountRequest);
    }
}