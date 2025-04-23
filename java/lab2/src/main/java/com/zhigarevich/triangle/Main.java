package com.zhigarevich.triangle;

import com.zhigarevich.triangle.repository.TriangleRepository;
import com.zhigarevich.triangle.repository.impl.TriangleRepositoryImpl;
import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.entity.TriangleType;
import com.zhigarevich.triangle.exception.TriangleException;
import com.zhigarevich.triangle.exception.TriangleValidationException;
import com.zhigarevich.triangle.parser.TriangleParser;
import com.zhigarevich.triangle.parser.impl.TriangleParserImpl;
import com.zhigarevich.triangle.reader.TriangleReader;
import com.zhigarevich.triangle.reader.impl.TriangleReaderImpl;
import com.zhigarevich.triangle.service.TriangleAnalysisService;
import com.zhigarevich.triangle.service.TriangleCalculationService;
import com.zhigarevich.triangle.service.TriangleService;
import com.zhigarevich.triangle.service.impl.TriangleAnalysisServiceImpl;
import com.zhigarevich.triangle.service.impl.TriangleCalculationServiceImpl;
import com.zhigarevich.triangle.service.impl.TriangleServiceImpl;
import com.zhigarevich.triangle.validator.TriangleValidator;
import com.zhigarevich.triangle.validator.impl.TriangleValidatorImpl;
import com.zhigarevich.triangle.warehouse.TriangleWarehouse;
import com.zhigarevich.triangle.writer.ResultWriter;
import com.zhigarevich.triangle.writer.impl.JsonResultWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            TriangleValidator validator = TriangleValidatorImpl.getInstance();

            TriangleCalculationService calculationService = TriangleCalculationServiceImpl.getInstance(validator);
            TriangleRepository triangleRepository = TriangleRepositoryImpl.getInstance();
            TriangleService triangleService = TriangleServiceImpl.getInstance(triangleRepository, validator);
            TriangleWarehouse warehouse = TriangleWarehouse.getInstance();

            TriangleAnalysisService analysisService = TriangleAnalysisServiceImpl.getInstance(validator, calculationService);

            TriangleReader reader = TriangleReaderImpl.getInstance();
            TriangleParser parser = TriangleParserImpl.getInstance();
            ResultWriter writer = JsonResultWriter.getInstance();

            String filePath = "data/triangles.txt";

            List<String> lines = reader.readTriangleData(filePath);
            logger.info("Successfully read {} lines from file: {}", lines.size(), filePath);

            List<Double[]> trianglesData = parser.parseTriangles(lines);
            logger.info("Successfully parsed {} triangle data entries", trianglesData.size());
            int id = 0;

            for (Double[] data : trianglesData) {
                try {
                    boolean triangleAdded = triangleService.add(new Triangle(id, data[1], data[2], data[3]));
                    if (!triangleAdded) {
                        logger.error("Triangle not added: {}", triangleAdded);
                    }
                    id++;
                } catch (TriangleValidationException e) {
                    logger.error(e.getMessage());
                }
            }

            List<Triangle> validTriangles = triangleService.findAll();

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

            logAdditionalTriangleInfo(validTriangles, analysisService, calculationService, warehouse);
            validTriangles.forEach(triangle -> {
                logger.info("Triangle: {}; \t\n area: {}; \t\n perimeter: {}.",
                        triangle,
                        warehouse.findParam(triangle.getId()).getArea(),
                        warehouse.findParam(triangle.getId()).getPerimeter());
            });
            List<Triangle> arbitraryTriangles = triangleService.findAllArbitrary();
            logger.info("All ARBITRARY triangles: ");
            for (var triangle: arbitraryTriangles) {
                logger.info(triangle);
            }
        } catch (TriangleException e) {
            logger.error("Triangle processing error: {}", e.getMessage(), e);
        }
    }

    private static void logAdditionalTriangleInfo(List<Triangle> triangles,
                                                  TriangleAnalysisService analysisService,
                                                  TriangleCalculationService calculationService,
                                                  TriangleWarehouse warehouse) {
        logger.info("Detailed triangle information:");

        for (Triangle triangle : triangles) {
            try {
                analysisService.determineType(triangle);
                double area = calculationService.calculateArea(triangle);
                double perimeter = calculationService.calculatePerimeter(triangle);
                warehouse.put(triangle.getId(), area, perimeter);
            } catch (TriangleException e) {
                logger.error("Error processing triangle ID {}: {}",
                        triangle.getId(), e.getMessage());
            }
        }
    }
}