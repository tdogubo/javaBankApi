package com.etz.bankapi.service;

import com.etz.bankapi.config.TransactionType;
import com.etz.bankapi.domain.request.AccountStatusRequest;
import com.etz.bankapi.domain.request.CreateAccountRequest;
import com.etz.bankapi.domain.request.CreateDepositRequest;
import com.etz.bankapi.domain.request.CreateTransferRequest;
import com.etz.bankapi.domain.response.*;
import com.etz.bankapi.model.*;
import com.etz.bankapi.repository.AccountRepository;
import com.etz.bankapi.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    protected void debitAccount(CreateTransferRequest request) {
        Optional<Account> user = accountRepository.findByAccountNumber(request.getSenderAccountNumber());
        if (user.isPresent()) {
            Account accountToBeDebited = user.get();
            if (accountToBeDebited.getIsActive() && accountToBeDebited.getAccountBalance() >= request.getAmount()) {
                accountToBeDebited.setAccountBalance(accountToBeDebited.getAccountBalance() - request.getAmount());
            }
            Transactions transaction = new Transactions();
            transaction.setAmount(request.getAmount());
            transaction.setTransactionType(TransactionType.DEBIT);
            transaction.setAccountNumber(accountToBeDebited.getAccountNumber());
            transaction.setAccount(accountToBeDebited);
            transaction.setTransactionId(request.getTransactionId());
            accountRepository.save(accountToBeDebited);
            transactionRepository.save(transaction);
        }

    }

    protected Account creditAccount(CreateDepositRequest request) {
        Optional<Account> user = accountRepository.findByAccountNumber(request.getAccountNumber());
        if (user.isPresent()) {
            Account accountToBeCredited = user.get();
            if (accountToBeCredited.getIsActive()) {

                accountToBeCredited.setAccountBalance(accountToBeCredited.getAccountBalance() + request.getAmount());
                Transactions transaction = new Transactions();
                transaction.setTransactionId(request.getTransactionId());
                transaction.setAmount(request.getAmount());
                transaction.setTransactionType(TransactionType.CREDIT);
                transaction.setTransactionId(request.getTransactionId());
                transaction.setAccountNumber(request.getAccountNumber());
                transaction.setAccount(accountToBeCredited);
                accountRepository.save(accountToBeCredited);
                transactionRepository.save(transaction);
            }
            return accountToBeCredited;
        }
        return null;
    }

    protected void creditAccount(CreateTransferRequest request) {
        Optional<Account> user = accountRepository.findByAccountNumber(request.getRecipientAccountNumber());
        if (user.isPresent()) {
            Account accountToBeCredited = user.get();
            if (accountToBeCredited.getIsActive()) {

                accountToBeCredited.setAccountBalance(accountToBeCredited.getAccountBalance() + request.getAmount());
                accountRepository.save(accountToBeCredited);
                Transactions transaction = new Transactions();
                transaction.setAmount(request.getAmount());
                transaction.setTransactionType(TransactionType.CREDIT);
                transaction.setAccountNumber(accountToBeCredited.getAccountNumber());
                transaction.setAccount(accountToBeCredited);
                transaction.setTransactionId(request.getTransactionId());
                transactionRepository.save(transaction);
            }
        }
    }

    protected CreateAccountResponse accountEntityToCreateAccountResponse(Account account) {
        CreateAccountResponse response = new CreateAccountResponse();
        response.setAccountNumber(account.getAccountNumber());
        response.setAccountType(account.getAccountType());
        return response;
    }

    public ResponseEntity<AppResponse<CreateAccountResponse>> createAccount(CreateAccountRequest createAccountRequest) {
        try {

            User user = userService.fetchUserFromDB(createAccountRequest.getUserId());
            if (user == null) {
                return new ResponseEntity<>(new AppResponse<>(false, "User does not exist!!"), HttpStatus.FORBIDDEN);
            }
            Account account = createAccountRequest.getAccountType().equals("current") ? new CurrentAccount() : new SavingsAccount();

            account.setAccountCreatedOn();
            account.setAccountNumber();
            if (accountRepository.findByAccountNumber(account.getAccountNumber()).isPresent()) {
                createAccount(createAccountRequest);
            }
            account.setPin(passwordEncoder.encode(createAccountRequest.getPin()));
            account.setAccountType(createAccountRequest.getAccountType());
            account.setAccountBalance(0.0);
            account.setIsActive(true);
            account.setUser(user);
            user.setAccount(account.getUser().getAccount());
            accountRepository.save(account);
            CreateAccountResponse response = accountEntityToCreateAccountResponse(account);
            return new ResponseEntity<>(new AppResponse<>(true, response), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            log.info(e.getLocalizedMessage());
            return new ResponseEntity<>(new AppResponse<>(false, "Account already exists"), HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<AppResponse<CreateDepositResponse>> makeDeposit(CreateDepositRequest request) {
        if (!accountRepository.findByAccountNumber(request.getAccountNumber()).isPresent()) {
            return new ResponseEntity<>(new AppResponse<>(false, "Wrong account number!!"), HttpStatus.BAD_REQUEST);
        }
        Account account = creditAccount(request);
        CreateDepositResponse response = new CreateDepositResponse();
        assert account != null;
        response.setAccountNumber(account.getAccountNumber());
        response.setTransactionId(request.getTransactionId());
        response.setAmount(request.getAmount());
        response.setAccountBalance(account.getAccountBalance());
        return new ResponseEntity<>(new AppResponse<>(true, response), HttpStatus.CREATED);

    }

    public ResponseEntity<AppResponse<CreateTransferResponse>> makeTransfer(CreateTransferRequest request) {
        Optional<Account> debitUser = accountRepository.findByAccountNumber(request.getSenderAccountNumber());
        if (debitUser.isPresent() && debitUser.get().getIsActive()) {
            if (debitUser.get().getAccountBalance() >= request.getAmount()) {
                if (passwordEncoder.matches(request.getPin(), debitUser.get().getPin())) {
                    creditAccount(request);
                    debitAccount(request);
                    CreateTransferResponse response = new CreateTransferResponse();
                    response.setTransactionId(request.getTransactionId());
                    response.setAccountBalance(debitUser.get().getAccountBalance());
                    response.setAmount(request.getAmount());
                    response.setAccountNumber(debitUser.get().getAccountNumber());
                    return new ResponseEntity<>(new AppResponse<>(true, response), HttpStatus.OK);
                }
                return new ResponseEntity<>(new AppResponse<>(false, "Invalid details"), HttpStatus.FORBIDDEN);

            }
            return new ResponseEntity<>(new AppResponse<>(false, "Insufficient balance!!"), HttpStatus.BAD_REQUEST);


        }
        return new ResponseEntity<>(new AppResponse<>(false, "Invalid account number!!"), HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<AppResponse<AccountStatusResponse>> accountStatus(AccountStatusRequest request) {
        Optional<Account> account = accountRepository.findByAccountNumber(request.getAccountNumber());
        if (account.isPresent()) {
            boolean status = request.getAccountStatus().equalsIgnoreCase("activate");
            account.get().setIsActive(status);
            accountRepository.save(account.get());
            AccountStatusResponse response = new AccountStatusResponse();
            response.setAccountNumber(account.get().getAccountNumber());
            response.setAccountStatus(account.get().getIsActive() ? "Activated" : "Deactivated");
            return new ResponseEntity<>(new AppResponse<>(true, response), HttpStatus.OK);
        }
        return new ResponseEntity<>(new AppResponse<>(true, "Error"), HttpStatus.BAD_REQUEST);

    }
}
