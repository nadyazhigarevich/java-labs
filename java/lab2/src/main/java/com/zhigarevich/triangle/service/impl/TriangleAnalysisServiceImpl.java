package com.zhigarevich.triangle.service.impl;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.entity.TriangleType;
import com.zhigarevich.triangle.exception.TriangleException;
import com.zhigarevich.triangle.service.TriangleAnalysisService;
import com.zhigarevich.triangle.service.TriangleCalculationService;
import com.zhigarevich.triangle.validator.TriangleValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TriangleAnalysisServiceImpl implements TriangleAnalysisService {
    private static final Logger logger = LogManager.getLogger(TriangleAnalysisServiceImpl.class);
    private final TriangleValidator validator;
    private final TriangleCalculationService calculationService;

    public TriangleAnalysisServiceImpl(TriangleValidator validator,
                                       TriangleCalculationService calculationService) {
        this.validator = validator;
        this.calculationService = calculationService;
    }

    @Override
    public Map<TriangleType, Integer> countTrianglesByType(List<Triangle> triangles) {
        Map<TriangleType, Integer> counts = new HashMap<>();
        for (TriangleType type : TriangleType.values()) {
            counts.put(type, 0);
        }

        for (Triangle triangle : triangles) {
            try {
                if (!validator.isValid(triangle)) {
                    logger.error("Invalid triangle with ID: {}", triangle.getId());
                    continue;
                }

                TriangleType type = determineType(triangle);
                if (type != null) {
                    counts.put(type, counts.get(type) + 1);
                }
            } catch (TriangleException e) {
                logger.error("Error processing triangle ID {}: {}", triangle.getId(), e.getMessage());
            }
        }
        return counts;
    }

    @Override
    public Map<TriangleType, Triangle> findLargestTrianglesByType(List<Triangle> triangles) {
        Map<TriangleType, Triangle> largest = new HashMap<>();
        for (TriangleType type : TriangleType.values()) {
            largest.put(type, null);
        }

        for (Triangle triangle : triangles) {
            try {
                if (!validator.isValid(triangle)) {
                    continue;
                }

                TriangleType type = determineType(triangle);
                if (type != null) {
                    Triangle currentLargest = largest.get(type);
                    if (currentLargest == null ||
                            calculationService.calculateArea(triangle) > calculationService.calculateArea(currentLargest)) {
                        largest.put(type, triangle);
                    }
                }
            } catch (TriangleException e) {
                logger.error("Error processing triangle ID {}: {}", triangle.getId(), e.getMessage());
            }
        }
        return largest;
    }

    @Override
    public Map<TriangleType, Triangle> findSmallestTrianglesByType(List<Triangle> triangles) {
        Map<TriangleType, Triangle> smallest = new HashMap<>();
        for (TriangleType type : TriangleType.values()) {
            smallest.put(type, null);
        }

        for (Triangle triangle : triangles) {
            try {
                if (!validator.isValid(triangle)) {
                    continue;
                }

                TriangleType type = determineType(triangle);
                if (type != null) {
                    Triangle currentSmallest = smallest.get(type);
                    if (currentSmallest == null ||
                            calculationService.calculateArea(triangle) < calculationService.calculateArea(currentSmallest)) {
                        smallest.put(type, triangle);
                    }
                }
            } catch (TriangleException e) {
                logger.error("Error processing triangle ID {}: {}", triangle.getId(), e.getMessage());
            }
        }
        return smallest;
    }

    @Override
    public TriangleType determineType(Triangle triangle) throws TriangleException {
        if (!validator.isValid(triangle)) {
            logger.error("Cannot determine type for invalid triangle ID: {}", triangle.getId());
            throw new TriangleException("Invalid triangle");
        }

        double a = triangle.getA();
        double b = triangle.getB();
        double c = triangle.getC();

        int equalSidesCount = countEqualSides(a, b, c);

        return switch (equalSidesCount) {
            case 3 -> TriangleType.EQUILATERAL;
            case 1 -> TriangleType.ISOSCELES;
            case 0 -> isRightAngleTriangle(triangle) ? TriangleType.RECTANGULAR : TriangleType.ARBITRARY;
            default -> {
                logger.error("Unexpected equal sides count: {} for triangle ID: {}", equalSidesCount, triangle.getId());
                yield null;
            }
        };
    }

    private boolean isRightAngleTriangle(Triangle triangle) throws TriangleException {
        double a = triangle.getA();
        double b = triangle.getB();
        double c = triangle.getC();

        return switch (findLargestSide(a, b, c)) {
            case 0 -> Math.abs(a - Math.hypot(b, c)) < 1e-10;
            case 1 -> Math.abs(b - Math.hypot(a, c)) < 1e-10;
            case 2 -> Math.abs(c - Math.hypot(a, b)) < 1e-10;
            default -> false;
        };
    }

    private int countEqualSides(double a, double b, double c) {
        int count = 0;
        if (Math.abs(a - b) < 1e-10) count++;
        if (Math.abs(b - c) < 1e-10) count++;
        if (Math.abs(a - c) < 1e-10) count++;
        return count;
    }

    private int findLargestSide(double a, double b, double c) {
        if (a >= b && a >= c) return 0;
        if (b >= a && b >= c) return 1;
        return 2;
    }
}