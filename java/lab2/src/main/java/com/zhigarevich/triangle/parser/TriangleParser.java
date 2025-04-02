package com.zhigarevich.triangle.parser;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.exception.TriangleException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TriangleParser {
    private static final int REQUIRED_PARTS_COUNT = 4;
    private static final String DELIMITER = ",";
    private static final String LINE_FORMAT_MESSAGE = "Line must contain exactly 4 comma-separated values: id,sideA,sideB,sideC";

    public List<Triangle> parseTriangles(List<String> lines) throws TriangleException {
        Objects.requireNonNull(lines, "Lines list cannot be null");
        List<Triangle> triangles = new ArrayList<>();

        for (String line : lines) {
            if (line != null && !line.trim().isEmpty()) {
                triangles.add(parseTriangle(line));
            }
        }
        return triangles;
    }

    public Triangle parseTriangle(String line) throws TriangleException {
        Objects.requireNonNull(line, "Line cannot be null");

        String trimmedLine = line.trim();
        if (trimmedLine.isEmpty()) {
            throw new TriangleException("Empty line cannot be parsed");
        }

        String[] parts = trimmedLine.split(DELIMITER);
        if (parts.length != REQUIRED_PARTS_COUNT) {
            throw new TriangleException(LINE_FORMAT_MESSAGE + ". Actual: " + trimmedLine);
        }

        try {
            int id = parseId(parts[0]);
            double sideA = parseSide(parts[1], "sideA");
            double sideB = parseSide(parts[2], "sideB");
            double sideC = parseSide(parts[3], "sideC");

            return new Triangle(id, sideA, sideB, sideC);
        } catch (NumberFormatException e) {
            throw new TriangleException("Invalid number format in line: " + trimmedLine, e);
        }
    }

    private int parseId(String idStr) throws NumberFormatException {
        String trimmed = idStr.trim();
        if (trimmed.isEmpty()) {
            throw new NumberFormatException("ID cannot be empty");
        }
        return Integer.parseInt(trimmed);
    }

    private double parseSide(String sideStr, String sideName) throws NumberFormatException {
        String trimmed = sideStr.trim();
        if (trimmed.isEmpty()) {
            throw new NumberFormatException(sideName + " cannot be empty");
        }
        double value = Double.parseDouble(trimmed);
        if (value <= 0) {
            throw new NumberFormatException(sideName + " must be positive");
        }
        return value;
    }
}