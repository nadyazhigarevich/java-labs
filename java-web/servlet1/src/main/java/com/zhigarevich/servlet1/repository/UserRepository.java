package com.zhigarevich.servlet1.repository;

import com.zhigarevich.servlet1.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    void updateVerificationStatus(String email, boolean verified);
}
