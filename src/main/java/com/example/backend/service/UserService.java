package com.example.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend.model.dao.User;
import com.example.backend.model.telegram.TelegramUserResponse;
import com.example.backend.repository.UserRepository;
import com.example.backend.utility.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public TelegramUserResponse getUserByTelegramId(Long telegramId) {
        User user = userRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No user found with this telegram account"));
        return TelegramUserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .telegramId(user.getTelegramId())
                .token(jwtUtil.generateToken(user.getId()))
                .build();
    }

    // public User getUserByEmail(String email) {
    // User user = userRepository.findByEmail(email)
    // .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
    // "No user found with this email"));
    // return UserMapper.toUserResponse(user);
    // }

    public User updateTelegramIdByEmail(String email, Long telegramId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid email"));
        user.setTelegramId(telegramId);
        return userRepository.save(user);
    }

}
