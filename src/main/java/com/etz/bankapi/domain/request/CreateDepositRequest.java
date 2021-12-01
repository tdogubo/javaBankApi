package com.etz.bankapi.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDepositRequest {
    private Long userId;
    private Long accountNumber;
    private Double amount;
    private String depositType;


}
