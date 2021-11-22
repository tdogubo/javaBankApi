package com.etz.bank_api.service;

import com.etz.bank_api.config.Response;
import com.etz.bank_api.domain.request.AccountRequest;
import com.etz.bank_api.domain.request.DepositRequest;
import com.etz.bank_api.domain.response.AccountResponse;
import com.etz.bank_api.domain.response.UserResponse;
import com.etz.bank_api.model.AccountModel;
import com.etz.bank_api.model.CurrentAccountModel;
import com.etz.bank_api.model.SavingsAccountModel;
import com.etz.bank_api.model.UserModel;
import com.etz.bank_api.repository.AccountRepository;
import com.etz.bank_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    public final UserRepository userRepository;
    public final AccountRepository accountRepository;
    public final UserService userService;

    public void debitAccount(AccountModel accountToBeDebited, DepositRequest request) {
        if (accountToBeDebited.getAccountType().equalsIgnoreCase("savings")) {
            if (accountToBeDebited.getAccountBalance() >= request.getAmount()) {
                accountToBeDebited.setAccountBalance(accountToBeDebited.getAccountBalance() - (request.getAmount() + (request.getAmount() * 0.1)));
            }
        } else if (accountToBeDebited.getAccountType().equalsIgnoreCase("current")) {
            accountToBeDebited.setAccountBalance(accountToBeDebited.getAccountBalance() - (request.getAmount() + (request.getAmount() * 0.2)));


        }
    }

    public void creditAccount(DepositRequest request) {
        Optional<AccountModel> user = accountRepository.findByAccountNumber(request.getAccountNumber());
        if (user.isPresent()) {
            AccountModel accountToBeCredited = user.get();
            if (accountToBeCredited.getIsActive()) {

                accountToBeCredited.setAccountBalance(accountToBeCredited.getAccountBalance() + request.getAmount());
                accountRepository.save(accountToBeCredited);
            }
        }
    }

    public AccountModel createUserAccount(AccountRequest accountRequest) {
        if (accountRequest.getAccountType().equals("savings")) {
            return new SavingsAccountModel();
        } else if (accountRequest.getAccountType().equals("current")) {
            return new CurrentAccountModel();
        } else {
            return null;
        }

    }

    public ResponseEntity<Response<AccountResponse>> createAccount(Long userId, AccountRequest accountRequest) {
        try {

        UserModel user = userService.fetchUserFromDB(userId);
        if (user == null) {
            return new ResponseEntity<>(new Response<>(false,"User does not exist!!"), HttpStatus.FORBIDDEN);
        }
        AccountModel account = createUserAccount(accountRequest);
        if (account == null) {
            return new ResponseEntity<>(new Response<>(false,"Account type not supported"), HttpStatus.BAD_REQUEST);
        }
        account.setAccountCreatedOn();
        account.setAccountNumber();
        account.setAccountBalance(0.0);
        account.setIsActive(true);
        user.setAccount(account);
        userRepository.save(user);
        return new ResponseEntity<>(new Response<>(true, "Account created successfully"), HttpStatus.CREATED);
        }catch (DataIntegrityViolationException e){
            System.out.println(e.getLocalizedMessage());
            return new ResponseEntity<>(new Response<>(false,"Account already exists"), HttpStatus.BAD_REQUEST);
        }

    }

    public void makeDeposit(DepositRequest depositRequest) {
        if (depositRequest.getDepositType().equals("cash")) {
            creditAccount(depositRequest);
        }
    }

    public void makeTransfer(DepositRequest depositRequest) {
        Optional<AccountModel> debitUser = accountRepository.findById(depositRequest.getUserId());
        if (debitUser.isPresent()) {
            creditAccount(depositRequest);
            AccountModel accountToBeDebited = debitUser.get();
            debitAccount(accountToBeDebited, depositRequest);
            accountRepository.save(accountToBeDebited);

        }
    }
}
