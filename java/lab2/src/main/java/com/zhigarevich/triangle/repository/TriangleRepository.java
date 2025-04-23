package com.zhigarevich.triangle.repository;

import com.zhigarevich.triangle.entity.Triangle;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public interface TriangleRepository {

    boolean add(Triangle triangle);

    List<Triangle> find(Predicate<Triangle> specification);

    List<Triangle> sort(Comparator<Triangle> sorting);

    List<Triangle> findAll();
}
