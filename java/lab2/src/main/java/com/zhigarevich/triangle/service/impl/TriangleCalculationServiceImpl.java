package com.zhigarevich.triangle.service.impl;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.exception.TriangleException;
import com.zhigarevich.triangle.service.TriangleCalculationService;
import com.zhigarevich.triangle.validator.TriangleValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TriangleCalculationServiceImpl implements TriangleCalculationService {
    private static final Logger logger = LogManager.getLogger(TriangleCalculationServiceImpl.class);
    private static TriangleCalculationService instance;
    private final TriangleValidator validator;

    private TriangleCalculationServiceImpl(TriangleValidator validator) {
        this.validator = validator;
    }

    public static TriangleCalculationService getInstance(TriangleValidator validator) {
        if (instance == null) {
            instance = new TriangleCalculationServiceImpl(validator);
        }
        return instance;
    }

    @Override
    public double calculateArea(Triangle triangle) throws TriangleException {
        if (!validator.isValid(triangle)) {
            logger.error("Attempt to calculate area for invalid triangle with ID: {}", triangle.getId());
            throw new TriangleException("Invalid triangle");
        }

        double semiPerimeter = calculatePerimeter(triangle) / 2;
        double area = Math.sqrt(semiPerimeter * (semiPerimeter - triangle.getA()) *
                (semiPerimeter - triangle.getB()) *
                (semiPerimeter - triangle.getC()));

        logger.debug("Calculated area {} for triangle ID: {}", area, triangle.getId());
        return area;
    }

    @Override
    public double calculatePerimeter(Triangle triangle) throws TriangleException {
        if (!validator.isValid(triangle)) {
            logger.error("Attempt to calculate perimeter for invalid triangle with ID: {}", triangle.getId());
            throw new TriangleException("Invalid triangle");
        }

        double perimeter = triangle.getA() + triangle.getB() + triangle.getC();
        logger.debug("Calculated perimeter {} for triangle ID: {}", perimeter, triangle.getId());
        return perimeter;
    }
}