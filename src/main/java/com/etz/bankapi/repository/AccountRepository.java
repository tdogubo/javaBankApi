package com.etz.bankapi.repository;

import com.etz.bankapi.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountModel, Long> {
    Optional<AccountModel> findByAccountNumber(Long accountNumber);
}
