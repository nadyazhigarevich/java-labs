package com.zhigarevich.triangle.factory.impl;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.exception.TriangleException;
import com.zhigarevich.triangle.factory.TriangleFactory;
import com.zhigarevich.triangle.validator.TriangleValidator;

public class TriangleFactoryImpl implements TriangleFactory {

    private static TriangleFactoryImpl instance;
    private final TriangleValidator validator;

    private TriangleFactoryImpl(TriangleValidator validator) {
        this.validator = validator;
    }

    public static TriangleFactory getInstance(TriangleValidator validator) {
        if (instance == null) {
            instance = new TriangleFactoryImpl(validator);
        }
        return instance;
    }

    @Override
    public Triangle createTriangle(int id, double sideA, double sideB, double sideC) throws TriangleException {
        validateSides(sideA, sideB, sideC);

        Triangle triangle = new Triangle(id, sideA, sideB, sideC);

        if (!validator.isValid(triangle)) {
            throw new TriangleException(String.format(
                    "Invalid triangle parameters: ID=%d, a=%.2f, b=%.2f, c=%.2f",
                    id, sideA, sideB, sideC));
        }

        return triangle;
    }

    private void validateSides(double sideA, double sideB, double sideC) throws TriangleException {
        if (sideA <= 0 || sideB <= 0 || sideC <= 0) {
            throw new TriangleException(String.format(
                    "All sides must be positive. Received: a=%.2f, b=%.2f, c=%.2f",
                    sideA, sideB, sideC));
        }
    }
}