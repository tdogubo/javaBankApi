package com.etz.bankapi.domain.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateAccountRequest {
    @NotNull(message = "User Id cannot be blank")
    @NotBlank(message = "User Id must be a number")
    private Long userId;
    private String accountType;
    private String pin;
}
