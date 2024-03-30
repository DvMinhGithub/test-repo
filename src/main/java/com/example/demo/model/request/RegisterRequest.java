package com.example.demo.model.request;

import com.example.demo.enums.Role;
import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
public class RegisterRequest {
    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email is not incorrect")
    private String email;

    @NotNull(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be 6 characters or more")
    @Size(max=16, message="Password must be less than 16 characters")
    private String password;

    private Role role;

    @Pattern(regexp = "(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b", message = "Invalid phone number")
    private String phoneNumber;

    private String address;

    private Boolean gender;

    private Timestamp dob;

    private String otp;
}
