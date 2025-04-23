package com.zhigarevich.triangle.service;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.exception.TriangleException;
import com.zhigarevich.triangle.exception.TriangleValidationException;

import java.util.List;

public interface TriangleService {

    List<Triangle> findAll();

    boolean add(Triangle triangle) throws TriangleException, TriangleValidationException;

    List<Triangle> findAllArbitrary();

    List<Triangle> sortByIdAsc();
}
