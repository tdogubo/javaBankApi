package com.etz.bank_api.domain.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private Long id;
    private Long accountNumber;
    private String accountType;
    private Double accountBalance;
    private LocalDate accountCreatedOn;
}
