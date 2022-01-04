package com.etz.bankapi.repository;

import com.etz.bankapi.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {
    @Autowired
    protected AccountRepository testRepo;

    @Test
    void canFindByAccountNumber() {
        //given
        Long accountNumber = 123456789L;
        //when
        Optional<Account> expectedAccount = testRepo.findByAccountNumber(accountNumber);
        //then
        assertThat(expectedAccount.isPresent()).isFalse();

    }
}