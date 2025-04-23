package com.zhigarevich.triangle.repository.impl;

import com.zhigarevich.triangle.repository.TriangleRepository;
import com.zhigarevich.triangle.entity.Triangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class TriangleRepositoryImpl implements TriangleRepository {

    private static TriangleRepository instance;

    private final List<Triangle> triangles = new ArrayList<>();

    private TriangleRepositoryImpl() {
    }

    public static TriangleRepository getInstance() {
        if (instance == null) {
            instance = new TriangleRepositoryImpl();
        }
        return instance;
    }

    @Override
    public boolean add(Triangle triangle) {
        return this.triangles.add(triangle);
    }

    @Override
    public List<Triangle> find(Predicate<Triangle> specification) {
        return this.triangles.stream()
                .filter(specification)
                .toList();
    }

    @Override
    public List<Triangle> sort(Comparator<Triangle> sorting) {
        var trianglesCopy = new ArrayList<>(this.triangles);
        trianglesCopy.sort(sorting);
        return trianglesCopy;
    }

    @Override
    public List<Triangle> findAll() {
        return new ArrayList<>(this.triangles);
    }
}
