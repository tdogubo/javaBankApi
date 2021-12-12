package com.etz.bankapi.domain.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class CreateTransferRequest {
    @NotNull(message = "Recipient's account number is required")
    private Long recipientAccountNumber;

    @NotNull(message = "Sender's account number is required")
    private Long senderAccountNumber;

    @NotEmpty(message = "Recipient's bank is required")
    private String recipientBank;

    private String recipientName;

    @NotNull
    @Min(100)
    private Double amount;

    @NotNull
    @Size(max = 4, min = 4)
    private String pin;

    private LocalDate transactionDate = LocalDate.now();
}
