package com.zhigarevich.triangle.specification.impl;

import com.zhigarevich.triangle.specification.FindTrianglesByType;
import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.entity.TriangleType;

public class FindTrianglesByTypeImpl implements FindTrianglesByType {

    private TriangleType type;

    public FindTrianglesByTypeImpl(TriangleType type) {
        this.type = type;
    }

    @Override
    public boolean test(Triangle triangle) {
        return triangle.getType().equals(this.type);
    }
}
