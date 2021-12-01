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
    private String address;
    private LocalDate dateOfBirth;
    // @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AccountModel> account;

//    public void setAccount(AccountModel account) {
//        this.account = account;
//    }

}
