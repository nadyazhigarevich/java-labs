package com.zhigarevich.servlet1.repository;

import com.zhigarevich.servlet1.model.PhoneBookEntry;

import java.util.List;

public interface PhoneBookRepository {
    List<PhoneBookEntry> findAllByUserId(int userId);
    PhoneBookEntry save(PhoneBookEntry entry);
    boolean update(PhoneBookEntry entry);
    boolean delete(int id, int userId);
}
