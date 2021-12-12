package com.etz.bankapi.domain.request;

import lombok.Data;

@Data
public class AccountStatusRequest {
    private Long accountNumber;
    private String accountStatus;
}
