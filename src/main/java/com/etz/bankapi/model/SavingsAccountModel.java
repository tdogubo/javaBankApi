package com.etz.bankapi.model;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@NoArgsConstructor
@Entity
@Getter
@Setter
@DiscriminatorValue("SAVINGS")
public class SavingsAccountModel extends AccountModel {
    private Long accountNumber;
    @Override
    public void setAccountNumber() {
        String start = "220";
        this.accountNumber = Long.parseLong(start +(Math.round(Math.random() * 100000000)));
    }

}
