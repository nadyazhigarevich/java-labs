package com.zhigarevich.triangle.parser.impl;

import com.zhigarevich.triangle.exception.TriangleException;
import com.zhigarevich.triangle.parser.TriangleParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TriangleParserImpl implements TriangleParser {
    private static final int REQUIRED_PARTS_COUNT = 4;
    private static final String DELIMITER = ",";
    private static final String LINE_FORMAT_MESSAGE = "Line must contain exactly 4 comma-separated values: id,sideA,sideB,sideC";

    @Override
    public List<Double[]> parseTriangles(List<String> lines) throws TriangleException {
        Objects.requireNonNull(lines, "Lines list cannot be null");
        List<Double[]> parsedData = new ArrayList<>();

        for (String line : lines) {
            if (line != null && !line.isBlank()) {
                parsedData.add(parseTriangleData(line));
            }
        }
        return parsedData;
    }

    @Override
    public Double[] parseTriangleData(String line) throws TriangleException {
        Objects.requireNonNull(line, "Line cannot be null");

        String trimmedLine = line.trim();
        if (trimmedLine.isBlank()) {
            throw new TriangleException("Empty or whitespace-only line cannot be parsed");
        }

        String[] parts = trimmedLine.split(DELIMITER);
        if (parts.length != REQUIRED_PARTS_COUNT) {
            throw new TriangleException(LINE_FORMAT_MESSAGE + ". Actual: " + trimmedLine);
        }

        try {
            int id = parseId(parts[0]);
            double sideA = parseSideValue(parts[1], "sideA");
            double sideB = parseSideValue(parts[2], "sideB");
            double sideC = parseSideValue(parts[3], "sideC");

            return new Double[]{(double)id, sideA, sideB, sideC};
        } catch (TriangleException e) {
            throw new TriangleException("Invalid data format in line: " + trimmedLine, e);
        }
    }

    private int parseId(String idStr) throws TriangleException {
        String trimmed = idStr.trim();
        if (trimmed.isBlank()) {
            throw new TriangleException("ID cannot be empty or whitespace-only");
        }
        try {
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException e) {
            throw new TriangleException("Invalid ID format: " + trimmed, e);
        }
    }

    private double parseSideValue(String sideStr, String sideName) throws TriangleException {
        String trimmed = sideStr.trim();
        if (trimmed.isBlank()) {
            throw new TriangleException(sideName + " cannot be empty or whitespace-only");
        }
        try {
            return Double.parseDouble(trimmed);
        } catch (NumberFormatException e) {
            throw new TriangleException("Invalid " + sideName + " format: " + trimmed, e);
        }
    }
}