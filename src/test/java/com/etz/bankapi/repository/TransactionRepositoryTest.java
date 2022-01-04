package com.etz.bankapi.repository;

import com.etz.bankapi.model.Transactions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    protected TransactionRepository testRepo;


    @Test
    void canFndAllByAccountNumber() {
        //given
        Long accountNumber = 22086057499L;
        Pageable page = PageRequest.of(0, 10);
        //when
        Page<Transactions> expected = testRepo.findAllByAccountNumber(accountNumber, page);
        //then
        assertThat(expected).isInstanceOf(Page.class);

    }


}