package com.zhigarevich.triangle.parser.impl;

import com.zhigarevich.triangle.exception.TriangleException;
import com.zhigarevich.triangle.parser.TriangleParser;

import java.util.ArrayList;
import java.util.List;

public class TriangleParserImpl implements TriangleParser {
    private static final int REQUIRED_PARTS_COUNT = 4;
    private static TriangleParserImpl instance;

    private TriangleParserImpl() {
    }

    public static TriangleParser getInstance() {
        if (instance == null) {
            instance = new TriangleParserImpl();
        }
        return instance;
    }

    @Override
    public List<Double[]> parseTriangles(List<String> lines) throws TriangleException {
        if (lines == null) {
            throw new TriangleException("Lines list cannot be null");
        }

        List<Double[]> parsedData = new ArrayList<>();
        for (String line : lines) {
            try {
                if (line != null && !line.isBlank()) {
                    parsedData.add(parseTriangleData(line));
                }
            } catch (TriangleException e) {
                throw new TriangleException("Failed to parse line: " + line, e);
            }
        }
        return parsedData;
    }

    @Override
    public Double[] parseTriangleData(String line) throws TriangleException {
        if (line == null) {
            throw new TriangleException("Line cannot be null");
        }

        String trimmedLine = line.trim();
        if (trimmedLine.isBlank()) {
            throw new TriangleException("Line cannot be empty or whitespace-only");
        }

        String[] parts = trimmedLine.split(",");
        if (parts.length != REQUIRED_PARTS_COUNT) {
            throw new TriangleException("Line must contain exactly 4 comma-separated values");
        }

        double sideA = parseDoubleValue(parts[1], "sideA");
        double sideB = parseDoubleValue(parts[2], "sideB");
        double sideC = parseDoubleValue(parts[3], "sideC");
        int id = parseId(parts[0]);

        return new Double[]{(double) id, sideA, sideB, sideC};
    }

    private int parseId(String idStr) throws TriangleException {
        String trimmed = idStr.trim();
        try {
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException e) {
            throw new TriangleException("Invalid ID format: " + trimmed, e);
        }
    }

    private double parseDoubleValue(String valueStr, String fieldName) throws TriangleException {
        String trimmed = valueStr.trim();
        try {
            return Double.parseDouble(trimmed);
        } catch (NumberFormatException e) {
            throw new TriangleException("Invalid " + fieldName + " format: " + trimmed, e);
        }
    }
}