package com.etz.bank_api.domain;

import lombok.Data;

import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.Period;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate dateOfBirth;

    @Transient
    private int age;

    public Integer getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

}
