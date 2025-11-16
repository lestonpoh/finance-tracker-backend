package com.lestonpoh.financeapp_backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lestonpoh.financeapp_backend.model.auth.LoginRequestDTO;
import com.lestonpoh.financeapp_backend.model.auth.RegisterRequestDTO;
import com.lestonpoh.financeapp_backend.model.dao.User;
import com.lestonpoh.financeapp_backend.repository.UserRepository;
import com.lestonpoh.financeapp_backend.utility.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequestDTO request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return jwtUtil.generateToken(user.getId());
    }

    public String login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        return jwtUtil.generateToken(user.getId());
    }
}
