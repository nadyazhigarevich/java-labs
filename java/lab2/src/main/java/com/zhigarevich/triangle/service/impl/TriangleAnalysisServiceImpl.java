package com.zhigarevich.triangle.service.impl;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.entity.TriangleType;
import com.zhigarevich.triangle.exception.TriangleException;
import com.zhigarevich.triangle.service.TriangleAnalysisService;
import com.zhigarevich.triangle.service.TriangleCalculationService;
import com.zhigarevich.triangle.validator.TriangleValidator;
import com.zhigarevich.triangle.writer.ResultWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TriangleAnalysisServiceImpl implements TriangleAnalysisService {
    private static final Logger logger = LogManager.getLogger(TriangleAnalysisServiceImpl.class);
    private final TriangleValidator validator;
    private final TriangleCalculationService calculationService;
    private final ResultWriter resultWriter;

    public TriangleAnalysisServiceImpl(TriangleValidator validator,
                                       TriangleCalculationService calculationService,
                                       ResultWriter resultWriter) {
        this.validator = validator;
        this.calculationService = calculationService;
        this.resultWriter = resultWriter;
    }

    @Override
    public void analyzeTriangles(List<Triangle> triangles) {
        Map<TriangleType, Integer> counts = new HashMap<>();
        Map<TriangleType, Triangle> largest = new HashMap<>();
        Map<TriangleType, Triangle> smallest = new HashMap<>();

        for (TriangleType type : TriangleType.values()) {
            counts.put(type, 0);
            largest.put(type, null);
            smallest.put(type, null);
        }

        for (Triangle triangle : triangles) {
            if (!validator.isValid(triangle)) {
                logger.error("Invalid triangle with ID: {}", triangle.getId());
                continue;
            }

            try {
                double area = calculationService.calculateArea(triangle);
                if (Double.isNaN(area)) {
                    logger.error("Invalid area calculated for triangle ID: {}", triangle.getId());
                    continue;
                }

                TriangleType type = determineType(triangle);
                if (type == null) {
                    logger.error("Could not determine type for triangle ID: {}", triangle.getId());
                    continue;
                }

                counts.put(type, counts.get(type) + 1);
                updateAreaExtremes(triangle, area, largest, smallest, type);
            } catch (Exception e) {
                logger.error("Error processing triangle ID {}: {}", triangle.getId(), e.getMessage());
            }
        }

        try {
            logResults(largest, smallest, counts);
            resultWriter.writeResults(counts, largest, smallest);
        } catch (Exception e) {
            logger.error("Error writing results: {}", e.getMessage());
        }
    }

    public void updateAreaExtremes(Triangle triangle, double area,
                                   Map<TriangleType, Triangle> largest,
                                   Map<TriangleType, Triangle> smallest,
                                   TriangleType type) {
        try {
            Triangle currentLargest = largest.get(type);
            if (currentLargest == null || area > calculationService.calculateArea(currentLargest)) {
                largest.put(type, triangle);
            }

            Triangle currentSmallest = smallest.get(type);
            if (currentSmallest == null || area < calculationService.calculateArea(currentSmallest)) {
                smallest.put(type, triangle);
            }
        } catch (Exception e) {
            logger.error("Error updating area extremes for triangle ID {}: {}", triangle.getId(), e.getMessage());
        }
    }

    @Override
    public TriangleType determineType(Triangle triangle) {
        if (!validator.isValid(triangle)) {
            logger.error("Cannot determine type for invalid triangle ID: {}", triangle.getId());
            return null;
        }

        double a = triangle.getA();
        double b = triangle.getB();
        double c = triangle.getC();

        int equalSidesCount = countEqualSides(a, b, c);

        try {
            return switch (equalSidesCount) {
                case 3 -> TriangleType.EQUILATERAL;
                case 1 -> TriangleType.ISOSCELES;
                case 0 -> isRightAngleTriangle(triangle) ? TriangleType.RECTANGULAR : TriangleType.ARBITRARY;
                default -> {
                    logger.error("Unexpected equal sides count: {} for triangle ID: {}", equalSidesCount, triangle.getId());
                    yield null;
                }
            };
        } catch (Exception e) {
            logger.error("Error determining triangle type for ID {}: {}", triangle.getId(), e.getMessage());
            return null;
        }
    }

    private void logResults(Map<TriangleType, Triangle> largest,
                            Map<TriangleType, Triangle> smallest,
                            Map<TriangleType, Integer> counts) throws TriangleException {
        for (TriangleType type : TriangleType.values()) {
            Triangle largestTriangle = largest.get(type);
            Triangle smallestTriangle = smallest.get(type);

            logger.info("{} triangles: Count: {}, Largest Area: {}, ID: {}, Smallest Area: {}, ID: {}",
                    type,
                    counts.get(type),
                    largestTriangle != null ? calculationService.calculateArea(largestTriangle) : "N/A",
                    largestTriangle != null ? largestTriangle.getId() : "N/A",
                    smallestTriangle != null ? calculationService.calculateArea(smallestTriangle) : "N/A",
                    smallestTriangle != null ? smallestTriangle.getId() : "N/A");
        }
    }

    public boolean isRightAngleTriangle(Triangle triangle) {
        try {
            double a = triangle.getA();
            double b = triangle.getB();
            double c = triangle.getC();

            return switch (findLargestSide(a, b, c)) {
                case 0 -> Math.abs(a - Math.hypot(b, c)) < 1e-10;
                case 1 -> Math.abs(b - Math.hypot(a, c)) < 1e-10;
                case 2 -> Math.abs(c - Math.hypot(a, b)) < 1e-10;
                default -> false;
            };
        } catch (Exception e) {
            logger.error("Error checking right angle for triangle ID {}: {}", triangle.getId(), e.getMessage());
            return false;
        }
    }

    public int countEqualSides(double a, double b, double c) {
        int count = 0;
        if (Math.abs(a - b) < 1e-10) count++;
        if (Math.abs(b - c) < 1e-10) count++;
        if (Math.abs(a - c) < 1e-10) count++;
        return count;
    }

    public int findLargestSide(double a, double b, double c) {
        if (a >= b && a >= c) return 0;
        if (b >= a && b >= c) return 1;
        return 2;
    }
}