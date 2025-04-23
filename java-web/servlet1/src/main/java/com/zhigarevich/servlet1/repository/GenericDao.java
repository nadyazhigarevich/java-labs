package com.zhigarevich.servlet1.repository;

import java.util.List;
import java.util.Optional;

public interface GenericDao<K, T> {

    List<T> findAll();

    Optional<T> findById(K id);

    void delete(K id);
}