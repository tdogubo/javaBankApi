package com.etz.bankapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "pin")
    private String pin;

    @Column(name = "account_type", insertable = false, updatable = false)
    private String accountType;
    private Double accountBalance;
    private Boolean isActive;
    private LocalDate accountCreatedOn;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "user_id"), name = "user_id")
    private User user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transactions> transactions;

    public void setAccountNumber() {
        this.accountNumber = (long) (Math.random() * 10000000000L);
    }

    public void setAccountCreatedOn() {
        this.accountCreatedOn = LocalDate.now();
    }
}
