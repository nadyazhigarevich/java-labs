package com.zhigarevich.triangle.validator;

import com.zhigarevich.triangle.entity.Triangle;

public class TriangleValidator {
    public boolean isValid(Triangle triangle) {
        double a = triangle.getA();
        double b = triangle.getB();
        double c = triangle.getC();

        return (a + b > c && a + c > b && b + c > a);
    }
}