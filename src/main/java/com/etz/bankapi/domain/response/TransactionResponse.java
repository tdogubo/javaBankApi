package com.etz.bankapi.domain.response;

import com.etz.bankapi.config.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String transactionId;
    private Long accountNumber;
    private Double amount;
    private TransactionType transactionType;
    private LocalDate transactionDate;
}
