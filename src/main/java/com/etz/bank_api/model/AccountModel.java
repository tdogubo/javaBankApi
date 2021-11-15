package com.etz.bank_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
public class AccountModel {
    @Id
    @Column(name = "user_id")
    private Long id;
    private String accountNumber;
    private String accountType;
    private String accountBalance;
    private String accountCurrency;
    private String accountStatus;
    private String accountCreatedOn;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserModel user;
}
