package com.zhigarevich.triangle.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TriangleReader {
    private String filePath;

    public TriangleReader(String filePath) {
        this.filePath = filePath;
    }

    public List<String> readTriangleData() throws IOException {
        List<String> triangleData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                triangleData.add(line);
            }
        }
        return triangleData;
    }
}