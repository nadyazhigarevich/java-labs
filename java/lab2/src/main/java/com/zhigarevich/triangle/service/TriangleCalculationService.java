package com.zhigarevich.triangle.service;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.exception.TriangleException;

public interface TriangleCalculationService {
    double calculateArea(Triangle triangle) throws TriangleException;
    double calculatePerimeter(Triangle triangle) throws TriangleException;
}