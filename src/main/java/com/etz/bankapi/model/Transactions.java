package com.etz.bankapi.model;

import com.etz.bankapi.config.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transactions {
    @Id
    private String transactionId;
    private Long accountNumber;
    private Double amount;
    private TransactionType transactionType;
    private LocalDate transactionDate = LocalDate.now();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "account_id"), name = "account_id")
    @JsonIgnore
    private Account account;
}




