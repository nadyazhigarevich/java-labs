package com.zhigarevich.triangle;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.entity.TriangleType;
import com.zhigarevich.triangle.exception.TriangleException;
import com.zhigarevich.triangle.factory.impl.TriangleFactoryImpl;
import com.zhigarevich.triangle.parser.impl.TriangleParserImpl;
import com.zhigarevich.triangle.reader.impl.TriangleReaderImpl;
import com.zhigarevich.triangle.service.impl.TriangleAnalysisServiceImpl;
import com.zhigarevich.triangle.service.impl.TriangleCalculationServiceImpl;
import com.zhigarevich.triangle.validator.impl.TriangleValidatorImpl;
import com.zhigarevich.triangle.writer.impl.JsonResultWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            TriangleValidatorImpl validator = new TriangleValidatorImpl();

            TriangleCalculationServiceImpl calculationService = new TriangleCalculationServiceImpl(validator);

            TriangleFactoryImpl factory = new TriangleFactoryImpl(validator);

            TriangleAnalysisServiceImpl analysisService = new TriangleAnalysisServiceImpl(validator, calculationService);

            TriangleReaderImpl reader = new TriangleReaderImpl();
            TriangleParserImpl parser = new TriangleParserImpl();
            JsonResultWriter writer = new JsonResultWriter();

            String filePath = "data/triangles.txt";

            List<String> lines = reader.readTriangleData(filePath);
            logger.info("Successfully read {} lines from file: {}", lines.size(), filePath);

            List<Double[]> trianglesData = parser.parseTriangles(lines);
            logger.info("Successfully parsed {} triangle data entries", trianglesData.size());

            List<Triangle> validTriangles = createAndValidateTriangles(factory, trianglesData);

            if (validTriangles.isEmpty()) {
                logger.warn("No valid triangles found in the input file");
                return;
            }

            logger.info("Created and validated {} triangles", validTriangles.size());

            Map<TriangleType, Integer> typeCounts = analysisService.countTrianglesByType(validTriangles);
            Map<TriangleType, Triangle> largestTriangles = analysisService.findLargestTrianglesByType(validTriangles);
            Map<TriangleType, Triangle> smallestTriangles = analysisService.findSmallestTrianglesByType(validTriangles);

            writer.writeResults(typeCounts, largestTriangles, smallestTriangles);
            logger.info("Results successfully written");

            logAdditionalTriangleInfo(validTriangles, analysisService, calculationService);

        } catch (TriangleException e) {
            logger.error("Triangle processing error: {}", e.getMessage(), e);
        }
    }

    private static List<Triangle> createAndValidateTriangles(TriangleFactoryImpl factory,
                                                             List<Double[]> trianglesData)
            throws TriangleException {
        List<Triangle> validTriangles = new ArrayList<>();
        int id = 1;

        for (Double[] data : trianglesData) {
            try {
                Triangle triangle = factory.createTriangle(id++, data[0], data[1], data[2]);
                validTriangles.add(triangle);
                logger.debug("Created valid triangle: {}", triangle);
            } catch (TriangleException e) {
                logger.warn("Failed to create triangle from data: {}, Error: {}",
                        Arrays.toString(data), e.getMessage());
            }
        }
        return validTriangles;
    }

    private static void logAdditionalTriangleInfo(List<Triangle> triangles,
                                                  TriangleAnalysisServiceImpl analysisService,
                                                  TriangleCalculationServiceImpl calculationService) {
        logger.info("Detailed triangle information:");

        for (Triangle triangle : triangles) {
            try {
                TriangleType type = analysisService.determineType(triangle);
                double area = calculationService.calculateArea(triangle);
                double perimeter = calculationService.calculatePerimeter(triangle);

                logger.info("Triangle ID: {}, Type: {}", triangle.getId(), type);
                logger.info("Sides: a={}, b={}, c={}",
                        triangle.getA(), triangle.getB(), triangle.getC());
                logger.info("Area: {}, Perimeter: {}", area, perimeter);

            } catch (TriangleException e) {
                logger.error("Error processing triangle ID {}: {}",
                        triangle.getId(), e.getMessage());
            }
        }
    }
}