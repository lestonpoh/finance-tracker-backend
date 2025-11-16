package com.lestonpoh.financeapp_backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lestonpoh.financeapp_backend.model.dao.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);

    boolean existsByEmail(String email);
}
