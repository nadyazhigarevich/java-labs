package com.zhigarevich.triangle.executor;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.entity.TriangleType;
import com.zhigarevich.triangle.exception.TriangleException;
import com.zhigarevich.triangle.service.TriangleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TriangleExecutor {
    private static final Logger logger = LogManager.getLogger(TriangleExecutor.class);
    private final TriangleService triangleService;

    public TriangleExecutor(TriangleService triangleService) {
        this.triangleService = triangleService;
    }

    public void execute(Triangle triangle) {
        try {
            logger.info("Executing operations for triangle: {}", triangle);
            executePerimeter(triangle);
            executeArea(triangle);
            executeType(triangle);
        } catch (Exception e) {
            logger.error("Error executing operations for triangle {}: {}", triangle, e.getMessage());
        }
    }

    public void executeAll(List<Triangle> triangles) {
        for (Triangle triangle : triangles) {
            execute(triangle);
        }
        try {
            triangleService.analyzeTriangles(triangles);
        } catch (TriangleException e) {
            throw new RuntimeException(e);
        }
    }

    private void executePerimeter(Triangle triangle) {
        try {
            double perimeter = triangleService.calculatePerimeter(triangle);
            logger.info("Perimeter of triangle {}: {}", triangle, perimeter);
        } catch (TriangleException e) {
            logger.warn(e.getMessage());
        }
    }

    private void executeArea(Triangle triangle) {
        try {
            double area = triangleService.calculateArea(triangle);
            logger.info("Area of triangle {}: {}", triangle, area);
        } catch (TriangleException e) {
            logger.warn(e.getMessage());
        }
    }

    private void executeType(Triangle triangle) {
        try {
            TriangleType type = triangleService.determineType(triangle);
            logger.info("Type of triangle {}: {}", triangle, type);
        } catch (TriangleException e) {
            logger.warn(e.getMessage());
        }
    }
}