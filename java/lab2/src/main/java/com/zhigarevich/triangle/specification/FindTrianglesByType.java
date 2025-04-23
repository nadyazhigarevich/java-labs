package com.zhigarevich.triangle.specification;

import com.zhigarevich.triangle.entity.Triangle;

import java.util.function.Predicate;

@FunctionalInterface
public interface FindTrianglesByType extends Predicate<Triangle> {
}
