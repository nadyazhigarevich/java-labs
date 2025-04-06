package com.zhigarevich.triangle.parser;

import com.zhigarevich.triangle.exception.TriangleException;
import java.util.List;

public interface TriangleParser {
    List<Double[]> parseTriangles(List<String> lines) throws TriangleException;
    Double[] parseTriangleData(String line) throws TriangleException;
}