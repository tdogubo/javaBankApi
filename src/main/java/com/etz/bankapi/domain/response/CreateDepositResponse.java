package com.etz.bankapi.domain.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CreateDepositResponse {
        private Long accountNumber;
        private Double amount;
        private LocalDate depositDate = LocalDate.now();
}
