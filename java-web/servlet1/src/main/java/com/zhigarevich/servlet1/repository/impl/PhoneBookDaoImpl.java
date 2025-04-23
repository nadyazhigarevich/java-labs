package com.zhigarevich.servlet1.repository.impl;

import com.zhigarevich.servlet1.model.PhoneBook;
import com.zhigarevich.servlet1.repository.PhoneBookDao;

import java.util.List;
import java.util.Optional;

public class PhoneBookDaoImpl implements PhoneBookDao {
    @Override
    public List<PhoneBook> findAllByUserId(int userId) {
        return List.of();
    }

    @Override
    public boolean update(PhoneBook entry) {
        return false;
    }

    @Override
    public boolean delete(int id, int userId) {
        return false;
    }

    @Override
    public List<PhoneBook> findAll() {
        return List.of();
    }

    @Override
    public Optional<PhoneBook> findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
