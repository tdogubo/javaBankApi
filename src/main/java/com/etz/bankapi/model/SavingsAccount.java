package com.etz.bankapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@NoArgsConstructor
@Entity
@Getter
@Setter
@DiscriminatorValue("SAVINGS")
public class SavingsAccount extends Account {
    private Long accountNumber;

    @Override
    public void setAccountNumber() {
        String start = "220";
        this.accountNumber = Long.parseLong(start + (Math.round(Math.random() * 100000000)));
    }

}
