package com.etz.bankapi.repository;

import com.etz.bankapi.model.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transactions, String> {

    Page<Transactions> findAllByAccountNumber(Long accountNumber, Pageable pageable);
}
