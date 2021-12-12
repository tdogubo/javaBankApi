package com.etz.bankapi.domain.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountStatusResponse {
    private Long accountNumber;
    private String accountStatus;
}
