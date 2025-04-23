package com.zhigarevich.phonebook.service.impl;

import com.zhigarevich.phonebook.dao.impl.UserDaoImpl;
import com.zhigarevich.phonebook.dto.LoginDto;
import com.zhigarevich.phonebook.dto.RegisterDto;
import com.zhigarevich.phonebook.model.User;
import com.zhigarevich.phonebook.service.AuthService;

public class AuthServiceImpl implements AuthService {

    private final UserDaoImpl userDao;

    public AuthServiceImpl(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @Override
    public User login(LoginDto dto) {
        return null;
    }

    @Override
    public User register(RegisterDto dto) {
        return null;
    }
}
