package com.etz.bankapi.domain.request;

import lombok.Data;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.Period;

@Data
public class CreateUserRequest {

    @NotNull(message = "First cannot be Null")
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last cannot be Null")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    @NotNull(message = "Password cannot be Null")
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must contain at least 8 characters, one digit, one uppercase, one lowercase, one special character and no whitespace")
    private String password;

    @NotNull(message = "Date of birth cannot be Null")
    private LocalDate dateOfBirth;

    @Transient
    private int age;

    public Integer getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

}