package com.etz.bankapi.service;

import com.etz.bankapi.config.Mapper;
import com.etz.bankapi.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {TransactionService.class})
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    protected TransactionRepository testRepo;

    @Autowired
    private TransactionService testService;


    protected Mapper mapper;

    @BeforeEach
    void setUp() {

        testService = new TransactionService(testRepo, mapper);
    }

    @Test
    void getTransaction() {
        //when
        testService.getTransaction("1");
        //then
        verify(testRepo).findById("1");
    }

    @Test
    void allTransactions() {
        //given
        Long accountNumber = 1L;
        int pageNumber = 0;
        Pageable page = PageRequest.of(pageNumber, 10);

        //when
        testService.allTransactions(accountNumber, pageNumber);

        //then
        verify(testRepo).findAllByAccountNumber(accountNumber, page);
    }

}