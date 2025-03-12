package com.zhigarevich.triangle;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.exception.TriangleException;
import com.zhigarevich.triangle.factory.TriangleFactory;
import com.zhigarevich.triangle.service.TriangleService;
import com.zhigarevich.triangle.service.TriangleServiceImpl;
import com.zhigarevich.triangle.validator.TriangleValidator;
import com.zhigarevich.triangle.executor.TriangleExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TriangleValidator validator = new TriangleValidator();
        TriangleService triangleService = new TriangleServiceImpl(validator);
        TriangleFactory triangleFactory = new TriangleFactory(validator);
        TriangleExecutor triangleExecutor = new TriangleExecutor(triangleService);

        int triangleCount = 10;
        List<Triangle> triangles = triangleFactory.createRandomTriangles(triangleCount);

        triangleExecutor.executeAll(triangles);
    }
}