package com.zhigarevich.servlet1.repository.impl;

import com.zhigarevich.servlet1.model.User;
import com.zhigarevich.servlet1.repository.UserDao;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void updateVerificationStatus(String email, boolean verified) {

    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public Optional<User> findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
