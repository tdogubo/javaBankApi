package com.etz.bankapi.service;

import com.etz.bankapi.config.Encoder;
import com.etz.bankapi.config.Mapper;
import com.etz.bankapi.domain.request.CreateAccountRequest;
import com.etz.bankapi.domain.request.CreateDepositRequest;
import com.etz.bankapi.model.Account;
import com.etz.bankapi.repository.AccountRepository;
import com.etz.bankapi.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;


//@ContextConfiguration(classes = {AccountService.class})
@ExtendWith(SpringExtension.class)
@RunWith(MockitoJUnitRunner.class)
class AccountServiceTest {

    @Mock
    protected AccountRepository accountTestRepository;

    @Mock
    protected TransactionRepository transactionTestRepository;

    @Mock
    protected Encoder testEncoder;

    @Mock
    protected UserService userTestService;

    @Mock
    protected Mapper mapper;

    @InjectMocks
    protected AccountService accountTestService;

    @BeforeEach
    void setUp() {
        accountTestService = new AccountService(accountTestRepository, transactionTestRepository, userTestService, testEncoder, mapper);
    }

    @Test
    void canDebitAccount() {
        //given
        CreateDepositRequest request = new CreateDepositRequest();

        request.setAmount(request.getAmount());
        request.setTransactionId("1");
        request.setAccountNumber(1L);

        //when
        accountTestService.makeDeposit(request);
        //then
        verify(accountTestRepository).findByAccountNumber(request.getAccountNumber());

    }

    @Test
    void canCreateAnAccount() {
        //given
        CreateAccountRequest request = new CreateAccountRequest();
        request.setAccountType("Savings");
        request.setPin("1234");
        request.setUserId(1L);

        //when
        accountTestService.createAccount(request);

        //then
        verify(userTestService).fetchUserFromDB(request.getUserId());
        when(accountTestRepository.save(any(Account.class))).thenReturn(new Account());
    }

    @Test
    void canMakeDeposit() {
    }

    @Test
    void canMakeTransfer() {
    }

    @Test
    void canAccountStatus() {
    }
}