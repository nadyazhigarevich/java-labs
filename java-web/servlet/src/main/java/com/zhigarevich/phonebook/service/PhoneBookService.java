package com.zhigarevich.phonebook.service;

import com.zhigarevich.phonebook.exception.BusinessException;
import com.zhigarevich.phonebook.model.PhoneBookEntry;

import java.sql.SQLException;
import java.util.List;

public interface PhoneBookService {

    List<PhoneBookEntry> findAll() throws BusinessException;

    List<PhoneBookEntry> findAllByUser(int id) throws BusinessException;

    PhoneBookEntry findById(int id) throws BusinessException;

    void save(PhoneBookEntry phoneBookEntry) throws BusinessException;

    void delete(int id) throws BusinessException;
}
