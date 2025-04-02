package com.zhigarevich.triangle.factory;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.validator.TriangleValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TriangleFactory {
    private static final Logger logger = LogManager.getLogger(TriangleFactory.class);
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
            logger.info("Created triangle with ID {} and sides ({}, {}, {})", triangle.getId(), a, b, c);
            return triangle;
        }
        return null;
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
                logger.info("Created random triangle with ID {} and sides ({}, {}, {})", triangle.getId(), a, b, c);
                return triangle;
            }
        }
    }

    public List<Triangle> createRandomTriangles(int count) {
        List<Triangle> triangles = new ArrayList<>();
        logger.info("Creating {} random triangles", count);
        for (int i = 0; i < count; i++) {
            triangles.add(createRandomTriangle());
        }
        logger.info("Successfully created {} random triangles", count);
        return triangles;
    }
}