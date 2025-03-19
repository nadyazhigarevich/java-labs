package com.zhigarevich.triangle.parser;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.exception.TriangleException;

import java.util.ArrayList;
import java.util.List;

public class TriangleParser {
    public List<Triangle> parseTriangles(List<String> lines) throws TriangleException {
        List<Triangle> triangles = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(","); // expecting input like "id,a,b,c"
            if (parts.length != 4) {
                throw new TriangleException("Invalid triangle data format: " + line);
            }
            try {
                int id = Integer.parseInt(parts[0].trim());
                double a = Double.parseDouble(parts[1].trim());
                double b = Double.parseDouble(parts[2].trim());
                double c = Double.parseDouble(parts[3].trim());
                triangles.add(new Triangle(id, a, b, c));
            } catch (NumberFormatException e) {
                throw new TriangleException("Error parsing triangle data: " + line, e);
            }
        }
        return triangles;
    }
}