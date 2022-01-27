package com.etz.bankapi.domain.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class CreateUserRequest {

    @NotNull(message = "First cannot be Null")
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name cannot be Null")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Address cannot be Null")
    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    @NotNull(message = "Password cannot be Null")
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must contain at least 8 characters, one digit, one uppercase, one lowercase, one special character and no whitespace")
    private String password;

    @NotNull(message = "PhoneNumber cannot be Null")
    @NotBlank(message = "PhoneNumber is required")
    private String phoneNumber;

    @NotNull(message = "User type cannot be Null")
    @NotBlank(message = "User type is required")
    private String type;

    @NotNull(message = "LGA cannot be Null")
    private String lga;

    @NotNull(message = "date of birth cannot be Null")
    private LocalDate dateOfBirth;
}