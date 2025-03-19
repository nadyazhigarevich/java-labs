package com.zhigarevich.triangle.factory;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.validator.TriangleValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TriangleFactory {
    private final TriangleValidator validator;
    private final Random random = new Random();
    private final List<Triangle> createdTriangles = new ArrayList<>();
    private int idCounter = 1;

    public TriangleFactory(TriangleValidator validator) {
        this.validator = validator;
    }

    public Triangle createTriangle(double a, double b, double c) {
        Triangle triangle = new Triangle(idCounter, a, b, c);
        if (validator.isValid(triangle)) {
            createdTriangles.add(triangle);
            idCounter++;
            return triangle;
        } else {
            System.err.println("Invalid triangle sides: (" + a + ", " + b + ", " + c + "). ID remains the same.");
            return null;
        }
    }

    public Triangle createRandomTriangle() {
        while (true) {
            int a = random.nextInt(10) + 1;
            int b = random.nextInt(10) + 1;
            int c = random.nextInt(a + b - 1) + 1;

            Triangle triangle = new Triangle(idCounter, a, b, c);
            if (validator.isValid(triangle)) {
                createdTriangles.add(triangle);
                idCounter++;
                return triangle;
            }
        }
    }

    public List<Triangle> createRandomTriangles(int count) {
        List<Triangle> triangles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            triangles.add(createRandomTriangle());
        }
        return triangles;
    }
}