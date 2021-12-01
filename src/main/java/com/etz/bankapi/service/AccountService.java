package com.etz.bankapi.service;

import com.etz.bankapi.domain.request.CreateAccountRequest;
import com.etz.bankapi.domain.request.CreateDepositRequest;
import com.etz.bankapi.domain.response.AccountResponse;
import com.etz.bankapi.domain.response.AppResponse;
import com.etz.bankapi.model.AccountModel;
import com.etz.bankapi.model.CurrentAccountModel;
import com.etz.bankapi.model.SavingsAccountModel;
import com.etz.bankapi.model.UserModel;
import com.etz.bankapi.repository.AccountRepository;
import com.etz.bankapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    public final UserRepository userRepository;
    public final AccountRepository accountRepository;
    public final UserService userService;

    public void debitAccount(AccountModel accountToBeDebited, CreateDepositRequest request) {
        if (accountToBeDebited.getAccountType().equalsIgnoreCase("savings")) {
            if (accountToBeDebited.getAccountBalance() >= request.getAmount()) {
                accountToBeDebited.setAccountBalance(accountToBeDebited.getAccountBalance() - (request.getAmount() + (request.getAmount() * 0.1)));
            }
        } else if (accountToBeDebited.getAccountType().equalsIgnoreCase("current")) {
            accountToBeDebited.setAccountBalance(accountToBeDebited.getAccountBalance() - (request.getAmount() + (request.getAmount() * 0.2)));


        }
    }

    public void creditAccount(CreateDepositRequest request) {
        Optional<AccountModel> user = accountRepository.findByAccountNumber(request.getAccountNumber());
        if (user.isPresent()) {
            AccountModel accountToBeCredited = user.get();
            if (accountToBeCredited.getIsActive()) {

                accountToBeCredited.setAccountBalance(accountToBeCredited.getAccountBalance() + request.getAmount());
                accountRepository.save(accountToBeCredited);
            }
        }
    }

    public ResponseEntity<AppResponse<AccountResponse>> createAccount(Long userId, CreateAccountRequest createAccountRequest) {
        try {

            UserModel user = userService.fetchUserFromDB(userId);
            if (user == null) {
                return new ResponseEntity<>(new AppResponse<>(false, "User does not exist!!"), HttpStatus.FORBIDDEN);
            }
            AccountModel account = createAccountRequest.getAccountType().equals("current") ? new CurrentAccountModel() : new SavingsAccountModel();

            account.setAccountCreatedOn();
            account.setAccountNumber();
            account.setAccountType(createAccountRequest.getAccountType());
            account.setAccountBalance(0.0);
            account.setIsActive(true);
            account.setUser(user);
            user.setAccount(account.getUser().getAccount());
            accountRepository.save(account);
            return new ResponseEntity<>(new AppResponse<>(true, "Account created successfully"), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            log.info(e.getLocalizedMessage());
            return new ResponseEntity<>(new AppResponse<>(false, "Account already exists"), HttpStatus.BAD_REQUEST);
        }

    }

    public void makeDeposit(CreateDepositRequest createDepositRequest) {
        if (createDepositRequest.getDepositType().equals("cash")) {
            creditAccount(createDepositRequest);
        }
    }

    public void makeTransfer(CreateDepositRequest createDepositRequest) {
        Optional<AccountModel> debitUser = accountRepository.findById(createDepositRequest.getUserId());
        if (debitUser.isPresent()) {
            creditAccount(createDepositRequest);
            AccountModel accountToBeDebited = debitUser.get();
            debitAccount(accountToBeDebited, createDepositRequest);
            accountRepository.save(accountToBeDebited);

        }
    }
}
