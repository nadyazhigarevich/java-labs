package com.zhigarevich.phonebook.dao;

import com.zhigarevich.phonebook.exception.BusinessException;
import com.zhigarevich.phonebook.model.PhoneBookEntry;

import java.sql.SQLException;
import java.util.List;

public interface PhoneBookDao extends BaseDao<Integer, PhoneBookEntry> {

    List<PhoneBookEntry> findAllByUser(int id) throws BusinessException;
}
