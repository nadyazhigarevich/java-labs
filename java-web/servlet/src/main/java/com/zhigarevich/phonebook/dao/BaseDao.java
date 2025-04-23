package com.zhigarevich.phonebook.dao;

import com.zhigarevich.phonebook.exception.BusinessException;
import com.zhigarevich.phonebook.model.AbstractEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BaseDao <K, E extends AbstractEntity>{

    List<E> findAll() throws BusinessException;

    Optional<E> findById(K id) throws BusinessException;

    void save(E entity) throws BusinessException;

    void delete(K id) throws BusinessException;
}
