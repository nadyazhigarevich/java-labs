package com.zhigarevich.triangle.reader;

import com.zhigarevich.triangle.exception.TriangleException;

import java.util.List;

public interface TriangleReader {
    List<String> readTriangleData(String filePath) throws TriangleException;
}