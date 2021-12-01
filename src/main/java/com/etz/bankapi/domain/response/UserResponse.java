package com.etz.bankapi.domain.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@NoArgsConstructor
@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;

    public Integer getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
