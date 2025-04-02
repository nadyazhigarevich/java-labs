package com.zhigarevich.triangle;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.exception.TriangleException;
import com.zhigarevich.triangle.executor.TriangleExecutor;
import com.zhigarevich.triangle.parser.TriangleParser;
import com.zhigarevich.triangle.reader.TriangleReader;
import com.zhigarevich.triangle.service.TriangleAnalysisService;
import com.zhigarevich.triangle.service.TriangleCalculationService;
import com.zhigarevich.triangle.service.impl.TriangleAnalysisServiceImpl;
import com.zhigarevich.triangle.service.impl.TriangleCalculationServiceImpl;
import com.zhigarevich.triangle.validator.TriangleValidator;
import com.zhigarevich.triangle.writer.ResultWriter;
import com.zhigarevich.triangle.writer.impl.JsonResultWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final String DEFAULT_FILE_PATH = "src/main/resources/triangles.txt";

    public static void main(String[] args) {
        String filePath = args.length > 0 ? args[0] : DEFAULT_FILE_PATH;

        try {
            TriangleValidator validator = new TriangleValidator();
            TriangleCalculationService calculationService = new TriangleCalculationServiceImpl(validator);
            ResultWriter resultWriter = new JsonResultWriter(calculationService);
            TriangleAnalysisService analysisService = new TriangleAnalysisServiceImpl(
                    validator, calculationService, resultWriter);
            TriangleExecutor executor = new TriangleExecutor(calculationService, analysisService);

            List<String> triangleData = new TriangleReader(filePath).readTriangleData();
            List<Triangle> triangles = new TriangleParser().parseTriangles(triangleData);

            executor.executeAll(triangles);

        } catch (FileNotFoundException e) {
            logger.error("File not found: {}", filePath);
        } catch (IOException e) {
            logger.error("Error reading file '{}': {}", filePath, e.getMessage());
        } catch (TriangleException e) {
            logger.error("Triangle processing error: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
        }
    }
}