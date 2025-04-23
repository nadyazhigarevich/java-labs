package com.zhigarevich.servlet1.service;

import com.zhigarevich.servlet1.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    void delete(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    void updateVerificationStatus(String email, boolean verified);
}
