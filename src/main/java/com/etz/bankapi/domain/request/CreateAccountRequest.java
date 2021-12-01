package com.etz.bankapi.domain.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {
    private Long id;
    private String accountNumber;
    private String accountType;
    private Double accountBalance;
    private String accountCurrency;
    private Boolean isActive;
    private LocalDate accountCreatedOn;
}
