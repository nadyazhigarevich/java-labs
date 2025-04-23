package com.zhigarevich.phonebook.dto;

public record RegisterDto(
        String username,
        String email,
        String password
) {
}
