package com.etz.bankapi.domain.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CreateTransferResponse {
    private String transactionId;
    private Long accountNumber;
    private Double amount;
    private Double accountBalance;
    private LocalDate transactionDate = LocalDate.now();
}
