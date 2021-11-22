package com.etz.bank_api.service;

import com.etz.bank_api.domain.request.AccountRequest;
import com.etz.bank_api.model.AccountModel;
import com.etz.bank_api.model.CurrentAccountModel;
import com.etz.bank_api.model.SavingsAccountModel;
import com.etz.bank_api.model.UserModel;
import com.etz.bank_api.repository.AccountRepository;
import com.etz.bank_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    public final UserRepository userRepository;
    public final AccountRepository accountRepository;
    public final UserService userService;

    public AccountModel createUserAccount(AccountRequest accountRequest) {
        if (accountRequest.getAccountType().equals("savings")) {
            return new SavingsAccountModel();
        } else if (accountRequest.getAccountType().equals("current")) {
            return new CurrentAccountModel();
        } else {
            return null;
        }

    }

    public String createAccount(Long userId, AccountRequest accountRequest) {
        UserModel user = userService.fetchUserFromDB(userId);
        if (user == null) {
            return "invalid details";
        }
        AccountModel account = createUserAccount(accountRequest);
        if (account == null) {
            return "Account type not supported";
        }
        account.setAccountCreatedOn();
        account.setAccountNumber();
        account.setAccountBalance(0.0);
        account.setIsActive(true);
        user.setAccount(account);
        userRepository.save(user);
        return "Account created successfully";

    }


    //    public void makeDeposit(Double amount) {
//
//    }
    public void makeDeposit(AccountRequest accountRequest) {
        Optional<AccountModel> debitUser = accountRepository.findById(accountRequest.getId());
        Optional<AccountModel> creditUser = accountRepository.findByAccountNumber(accountRequest.getAccountNumber());
        if (debitUser.isPresent() && creditUser.isPresent()) {
            AccountModel debitAccount = debitUser.get();
            AccountModel creditAccount = creditUser.get();

            if (depositType.equals("cash")) {
                creditAccount.setAccountBalance(creditAccount.getAccountBalance() + amount);
            }
            if (debitAccount.getAccountType().equals("savings")) {
                if (debitAccount.getAccountBalance() >= amount) {
                    debitAccount.setAccountBalance(debitAccount.getAccountBalance() - amount);


                    creditUser.ifPresent(accountModel -> System.out.println(accountModel.getAccountBalance()));

                }

            }
            accountRepository.save(debitAccount);
            accountRepository.save(creditAccount);

        }
//        debitAccount.getAccount().makeDeposit(amount);

//        Optional<AccountModel> account = accountRepository.findByAccountNumber(depositRequest.getAccountNumber());
//        if (!account.isPresent()) {
//            return "Account not found";
//        }
//        AccountModel creditAccount = account.get();
//        userRepository.save(user);
//        return "Account deposited successfully";
//    }


    }
}
