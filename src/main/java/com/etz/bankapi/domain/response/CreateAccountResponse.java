package com.etz.bankapi.domain.response;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CreateAccountResponse {
    private Long accountNumber;
    private String accountType;

}
