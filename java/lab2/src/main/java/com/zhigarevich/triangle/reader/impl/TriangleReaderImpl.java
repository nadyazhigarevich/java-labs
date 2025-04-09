package com.zhigarevich.triangle.reader.impl;

import com.zhigarevich.triangle.exception.TriangleException;
import com.zhigarevich.triangle.reader.TriangleReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class TriangleReaderImpl implements TriangleReader {
    @Override
    public List<String> readTriangleData(String filePath) throws TriangleException {
        try {
            Path path = Paths.get(filePath);
            try (Stream<String> lines = Files.lines(path)) {
                return lines.toList();  
            }
        } catch (IOException e) {
            throw new TriangleException("Error reading file: " + filePath, e);
        }
    }
}