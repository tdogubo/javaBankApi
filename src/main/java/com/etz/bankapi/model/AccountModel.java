package com.etz.bankapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@AllArgsConstructor
public class AccountModel {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    @Column(name = "account_type", insertable = false, updatable = false)
    private String accountType;
    private Double accountBalance;
    private Long accountNumber;
    private Boolean isActive;
    private LocalDate accountCreatedOn;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "user_id"), name = "user_id")
    private UserModel user;

    public void setAccountNumber() {
        this.accountNumber = (long) (Math.random() * 10000000000L);
    }

    public void setAccountCreatedOn() {
        this.accountCreatedOn = LocalDate.now();
    }
}
