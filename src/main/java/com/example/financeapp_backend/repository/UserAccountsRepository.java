package com.example.financeapp_backend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.financeapp_backend.model.dao.UserAccounts;

public interface UserAccountsRepository extends MongoRepository<UserAccounts, String> {
    Optional<UserAccounts> findByUserId(String userId);
}
