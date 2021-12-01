package com.etz.bankapi.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private Long accountNumber;
    private Long amount;
    private Double accountBalance;
    private LocalDate transactionDate;
}
