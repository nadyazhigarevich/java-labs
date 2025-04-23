package com.zhigarevich.servlet1.service;

import com.zhigarevich.servlet1.exception.BusinessException;
import com.zhigarevich.servlet1.model.PhoneBook;

import java.util.List;

public interface PhoneBookService {

    List<PhoneBook> findAll();

    PhoneBook findById(Long id) throws BusinessException;

    void delete(Long id);

    List<PhoneBook> findAllByUserId(int userId);

    boolean update(PhoneBook entry);
}
