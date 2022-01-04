package com.etz.bankapi.domain.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class CreateDepositRequest {
    private String transactionId = "REF" + Math.round(Math.random() * 1000000000);
    @NotNull(message = "Account number is required")
    @Size(min = 10, max = 10, message = "Account number incomplete must be 10 digits")
    private Long accountNumber;
    @NotNull(message = "Amount is required")
    @Min(value = 10, message = "Amount must be greater than 10")
    private Double amount;


}
