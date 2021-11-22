package com.etz.bank_api.domain.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.Period;

@Data
@NoArgsConstructor
public class UserRequest {
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
