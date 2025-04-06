package com.zhigarevich.triangle.service;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.entity.TriangleType;
import com.zhigarevich.triangle.exception.TriangleException;

import java.util.List;
import java.util.Map;

public interface TriangleAnalysisService {
    Map<TriangleType, Integer> countTrianglesByType(List<Triangle> triangles);
    Map<TriangleType, Triangle> findLargestTrianglesByType(List<Triangle> triangles);
    Map<TriangleType, Triangle> findSmallestTrianglesByType(List<Triangle> triangles);
    TriangleType determineType(Triangle triangle) throws TriangleException;
}