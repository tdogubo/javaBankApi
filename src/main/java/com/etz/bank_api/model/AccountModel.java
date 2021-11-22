package com.etz.bank_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@AllArgsConstructor
public class AccountModel {
    @Id
    @Column(name = "user_id")
    private Long id;
    @Column(name = "account_type", insertable = false, updatable = false)
    private String accountType;
    private Double accountBalance;
    private Long accountNumber;
    private Boolean isActive;
    private LocalDate accountCreatedOn;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserModel user;
    public void setAccountNumber() {
        this.accountNumber = (long) (Math.random() * 10000000000L);
    }
    public void setAccountCreatedOn() {
        this.accountCreatedOn = LocalDate.now();
    }
}
