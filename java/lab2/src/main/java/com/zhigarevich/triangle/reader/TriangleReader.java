package com.zhigarevich.triangle.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TriangleReader {
    private final Path filePath;

    public TriangleReader(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public List<String> readTriangleData() throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines.collect(Collectors.toList());
        }
    }
}