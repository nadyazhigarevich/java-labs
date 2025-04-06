package com.zhigarevich.triangle.validator.impl;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.validator.TriangleValidator;

public class TriangleValidatorImpl implements TriangleValidator {
    public boolean isValid(Triangle triangle) {
        double a = triangle.getA();
        double b = triangle.getB();
        double c = triangle.getC();

        return (a + b > c && a + c > b && b + c > a);
    }
}