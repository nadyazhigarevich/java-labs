package com.zhigarevich.triangle.service;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.entity.TriangleType;
import com.zhigarevich.triangle.exception.TriangleException;

import java.util.List;

public interface TriangleAnalysisService {
    TriangleType determineType(Triangle triangle) throws TriangleException;

    void analyzeTriangles(List<Triangle> triangles) throws TriangleException;
}