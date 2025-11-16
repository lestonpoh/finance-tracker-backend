package com.lestonpoh.financeapp_backend.model.auth;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String email;
    private String password;
    private String confirmPassword;
    private String username;
}
