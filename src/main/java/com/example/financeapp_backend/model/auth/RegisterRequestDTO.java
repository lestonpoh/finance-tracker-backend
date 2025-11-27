package com.example.financeapp_backend.model.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotNull(message = "Password is required")
    private String password;
    @NotNull(message = "Confirm password is required")
    private String confirmPassword;
    @NotNull(message = "Username is required")
    private String username;
}
