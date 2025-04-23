package com.zhigarevich.servlet1.repository;

import com.zhigarevich.servlet1.model.PhoneBook;

import java.util.List;

public interface PhoneBookDao extends GenericDao<Long, PhoneBook> {
    List<PhoneBook> findAllByUserId(int userId);
    boolean update(PhoneBook entry);
    boolean delete(int id, int userId);
}
