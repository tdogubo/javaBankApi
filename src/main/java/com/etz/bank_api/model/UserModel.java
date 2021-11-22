package com.etz.bank_api.model;

import javax.persistence.*;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate dateOfBirth;

    @OneToOne(mappedBy = "user" ,cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private AccountModel account;
    public AccountModel setAccount(AccountModel account) {
        this.account = account;
        account.setUser(this);
        return account;
    }
    
}
