package com.zhigarevich.triangle.service;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.entity.TriangleType;
import com.zhigarevich.triangle.exception.TriangleException;
import com.zhigarevich.triangle.validator.TriangleValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TriangleServiceImpl implements TriangleService {
    private static final Logger logger = LogManager.getLogger(TriangleServiceImpl.class);
    private final TriangleValidator validator;

    public TriangleServiceImpl(TriangleValidator validator) {
        this.validator = validator;
    }

    @Override
    public double calculateArea(Triangle triangle) throws TriangleException {
        validate(triangle);
        double semiPerimeter = calculatePerimeter(triangle) / 2;
        return Math.sqrt(semiPerimeter * (semiPerimeter - triangle.getA()) *
                (semiPerimeter - triangle.getB()) *
                (semiPerimeter - triangle.getC()));
    }

    @Override
    public double calculatePerimeter(Triangle triangle) throws TriangleException {
        validate(triangle);
        return triangle.getA() + triangle.getB() + triangle.getC();
    }

    @Override
    public TriangleType determineType(Triangle triangle) throws TriangleException {
        validate(triangle);
        if (triangle.getA() == triangle.getB() && triangle.getB() == triangle.getC()) {
            return TriangleType.EQUILATERAL;
        } else if (triangle.getA() == triangle.getB() ||
                triangle.getB() == triangle.getC() ||
                triangle.getA() == triangle.getC()) {
            return TriangleType.ISOSCELES;
        } else if (isRightAngleTriangle(triangle)) {
            return TriangleType.RECTANGULAR;
        } else {
            return TriangleType.ARBITRARY;
        }
    }

    public void analyzeTriangles(List<Triangle> triangles) throws TriangleException {
        Map<TriangleType, Integer> counts = new HashMap<>();
        Map<TriangleType, Triangle> largest = new HashMap<>();
        Map<TriangleType, Triangle> smallest = new HashMap<>();

        for (TriangleType type : TriangleType.values()) {
            counts.put(type, 0);
            largest.put(type, null);
            smallest.put(type, null);
        }

        for (Triangle triangle : triangles) {
            validate(triangle);
            TriangleType type = determineType(triangle);
            double area = calculateArea(triangle);
            counts.put(type, counts.get(type) + 1);
            updateAreaExtremes(triangle, area, largest, smallest, type);
        }

        logResults(largest, smallest, counts);
    }

    private void logResults(Map<TriangleType, Triangle> largest, Map<TriangleType, Triangle> smallest, Map<TriangleType, Integer> counts) throws TriangleException {
        for (TriangleType type : TriangleType.values()) {
            Triangle largestTriangle = largest.get(type);
            Triangle smallestTriangle = smallest.get(type);

            logger.info("{} triangles: Count: {}, Largest Area: {}, ID: {}, Smallest Area: {}, ID: {}",
                    type, counts.get(type),
                    largestTriangle != null ? calculateArea(largestTriangle) : "N/A",
                    largestTriangle != null ? largestTriangle.getId() : "N/A",
                    smallestTriangle != null ? calculateArea(smallestTriangle) : "N/A",
                    smallestTriangle != null ? smallestTriangle.getId() : "N/A");
        }
    }

    private void updateAreaExtremes(Triangle triangle, double area, Map<TriangleType, Triangle> largest, Map<TriangleType, Triangle> smallest, TriangleType type) throws TriangleException {
        if (largest.get(type) == null || area > calculateArea(largest.get(type))) {
            largest.put(type, triangle);
        }
        if (smallest.get(type) == null || area < calculateArea(smallest.get(type))) {
            smallest.put(type, triangle);
        }
    }

    private boolean isRightAngleTriangle(Triangle triangle) {
        return Math.pow(triangle.getA(), 2) + Math.pow(triangle.getB(), 2) == Math.pow(triangle.getC(), 2) ||
                Math.pow(triangle.getA(), 2) + Math.pow(triangle.getC(), 2) == Math.pow(triangle.getB(), 2) ||
                Math.pow(triangle.getB(), 2) + Math.pow(triangle.getC(), 2) == Math.pow(triangle.getA(), 2);
    }

    private void validate(Triangle triangle) throws TriangleException {
        if (!validator.isValid(triangle)) {
            throw new TriangleException("Invalid triangle sides");
        }
    }
}