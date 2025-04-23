package com.zhigarevich.triangle.writer.impl;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.entity.TriangleType;
import com.zhigarevich.triangle.exception.TriangleException;
import com.zhigarevich.triangle.writer.ResultWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class JsonResultWriter implements ResultWriter {
    private static final String OUTPUT_FILE = "data/triangle_results.json";

    private static ResultWriter instance;

    private JsonResultWriter() {
    }

    public static ResultWriter getInstance() {
        if (instance == null) {
            instance = new JsonResultWriter();
        }
        return instance;
    }

    @Override
    public void writeResults(Map<TriangleType, Integer> counts,
                             Map<TriangleType, Triangle> largest,
                             Map<TriangleType, Triangle> smallest) throws TriangleException {
        try {
            JSONObject jsonResult = new JSONObject();
            JSONArray resultsArray = new JSONArray();

            for (TriangleType type : TriangleType.values()) {
                JSONObject typeResult = new JSONObject();
                typeResult.put("type", type.toString());
                typeResult.put("count", counts.get(type));

                addTriangleToJson(typeResult, "largest", largest.get(type));
                addTriangleToJson(typeResult, "smallest", smallest.get(type));

                resultsArray.put(typeResult);
            }

            jsonResult.put("results", resultsArray);

            Path path = Paths.get(OUTPUT_FILE);
            Files.createDirectories(path.getParent());
            Files.writeString(path, jsonResult.toString(2));
        } catch (Exception e) {
            throw new TriangleException("Failed to save JSON results", e);
        }
    }

    private void addTriangleToJson(JSONObject parent, String key, Triangle triangle) throws TriangleException {
        if (triangle != null) {
            JSONObject triangleJson = new JSONObject();
            triangleJson.put("id", triangle.getId());
            triangleJson.put("sideA", triangle.getA());
            triangleJson.put("sideB", triangle.getB());
            triangleJson.put("sideC", triangle.getC());
            parent.put(key, triangleJson);
        }
    }
}