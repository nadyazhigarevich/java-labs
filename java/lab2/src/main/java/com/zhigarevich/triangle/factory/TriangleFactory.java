package com.zhigarevich.triangle.factory;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.exception.TriangleException;

public interface TriangleFactory {
    Triangle createTriangle(int id, double sideA, double sideB, double sideC) throws TriangleException;
}