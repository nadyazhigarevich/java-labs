package com.zhigarevich.triangle.writer;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.entity.TriangleType;
import com.zhigarevich.triangle.exception.TriangleException;

import java.util.Map;

public interface ResultWriter {
    void writeResults(Map<TriangleType, Integer> counts,
                      Map<TriangleType, Triangle> largest,
                      Map<TriangleType, Triangle> smallest) throws TriangleException;
}