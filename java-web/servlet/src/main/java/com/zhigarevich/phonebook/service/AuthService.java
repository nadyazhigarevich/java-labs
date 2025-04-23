package com.zhigarevich.phonebook.service;

import com.zhigarevich.phonebook.dto.LoginDto;
import com.zhigarevich.phonebook.dto.RegisterDto;
import com.zhigarevich.phonebook.model.User;

public interface AuthService {

    User login(LoginDto dto);

    User register(RegisterDto dto);
}
