package com.zhigarevich.triangle.service.impl;

import com.zhigarevich.triangle.repository.TriangleRepository;
import com.zhigarevich.triangle.sorting.impl.SortByIdSortingImpl;
import com.zhigarevich.triangle.specification.impl.FindTrianglesByTypeImpl;
import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.entity.TriangleType;
import com.zhigarevich.triangle.exception.TriangleValidationException;
import com.zhigarevich.triangle.service.TriangleService;
import com.zhigarevich.triangle.validator.TriangleValidator;

import java.util.List;

public class TriangleServiceImpl implements TriangleService {

    private static TriangleService instance;
    private final TriangleRepository triangleRepository;
    private final TriangleValidator validator;

    private TriangleServiceImpl(TriangleRepository repository, TriangleValidator validator) {
        this.triangleRepository = repository;
        this.validator = validator;
    }

    public static TriangleService getInstance(TriangleRepository repository, TriangleValidator validator) {
        if (instance == null) {
            instance = new TriangleServiceImpl(repository, validator);
        }
        return instance;
    }

    @Override
    public List<Triangle> findAll() {
        return triangleRepository.findAll();
    }

    @Override
    public boolean add(Triangle triangle) throws TriangleValidationException {
        if (!this.validator.isValid(triangle)) {
            throw new TriangleValidationException("Triangle invalid: %s".formatted(triangle));
        }
        return triangleRepository.add(triangle);
    }

    @Override
    public List<Triangle> findAllArbitrary() {
        return this.triangleRepository.find(new FindTrianglesByTypeImpl(TriangleType.ARBITRARY));
    }

    @Override
    public List<Triangle> sortByIdAsc() {
        return this.triangleRepository.sort(new SortByIdSortingImpl());
    }
}
