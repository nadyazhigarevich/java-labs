package com.zhigarevich.servlet1.service.impl;

import com.zhigarevich.servlet1.model.User;
import com.zhigarevich.servlet1.repository.UserDao;
import com.zhigarevich.servlet1.repository.impl.UserDaoImpl;
import com.zhigarevich.servlet1.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userRepository = new UserDaoImpl();

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public void updateVerificationStatus(String email, boolean verified) {

    }
}
