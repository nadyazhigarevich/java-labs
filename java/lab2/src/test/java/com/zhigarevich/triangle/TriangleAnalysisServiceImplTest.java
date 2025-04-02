package com.zhigarevich.triangle;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.entity.TriangleType;
import com.zhigarevich.triangle.exception.TriangleException;
import com.zhigarevich.triangle.service.TriangleCalculationService;
import com.zhigarevich.triangle.service.impl.TriangleAnalysisServiceImpl;
import com.zhigarevich.triangle.validator.TriangleValidator;
import com.zhigarevich.triangle.writer.ResultWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TriangleAnalysisServiceImplTest {

    private TriangleValidator triangleValidator;
    private TriangleCalculationService triangleCalculationService;
    private ResultWriter resultWriter;
    private TriangleAnalysisServiceImpl triangleAnalysisService;

    @BeforeEach
    void setUp() {
        triangleValidator = mock(TriangleValidator.class);
        triangleCalculationService = mock(TriangleCalculationService.class);
        resultWriter = mock(ResultWriter.class);
        triangleAnalysisService = new TriangleAnalysisServiceImpl(triangleValidator, triangleCalculationService, resultWriter);
    }

    @Test
    void analyzeTriangles_ShouldProcessValidTrianglesCorrectly() throws TriangleException {
        Triangle triangle1 = new Triangle(1, 3, 4, 5);
        Triangle triangle2 = new Triangle(2, 5, 5, 5);
        Triangle triangle3 = new Triangle(3, 4, 4, 2);

        when(triangleValidator.isValid(triangle1)).thenReturn(true);
        when(triangleValidator.isValid(triangle2)).thenReturn(true);
        when(triangleValidator.isValid(triangle3)).thenReturn(true);

        when(triangleCalculationService.calculateArea(triangle1)).thenReturn(6.0);
        when(triangleCalculationService.calculateArea(triangle2)).thenReturn(10.83);
        when(triangleCalculationService.calculateArea(triangle3)).thenReturn(8.0);

        triangleAnalysisService.analyzeTriangles(Arrays.asList(triangle1, triangle2, triangle3));

        ArgumentCaptor<Map<TriangleType, Integer>> countsCaptor = ArgumentCaptor.forClass(Map.class);
        verify(resultWriter).writeResults(countsCaptor.capture(), any(), any());

        Map<TriangleType, Integer> actualCounts = countsCaptor.getValue();
        int expectedRectangular = 1;
        int expectedEquilateral = 1;
        int expectedIsosceles = 1;

        assertEquals(expectedRectangular, actualCounts.getOrDefault(TriangleType.RECTANGULAR, 0));
        assertEquals(expectedEquilateral, actualCounts.getOrDefault(TriangleType.EQUILATERAL, 0));
        assertEquals(expectedIsosceles, actualCounts.getOrDefault(TriangleType.ISOSCELES, 0));
    }

    @Test
    void analyzeTriangles_ShouldSkipInvalidTriangles() throws TriangleException {
        Triangle validTriangle = new Triangle(1, 3, 4, 5);
        Triangle invalidTriangle = new Triangle(2, 1, 1, 3);

        when(triangleValidator.isValid(validTriangle)).thenReturn(true);
        when(triangleValidator.isValid(invalidTriangle)).thenReturn(false);
        when(triangleCalculationService.calculateArea(validTriangle)).thenReturn(6.0);

        triangleAnalysisService.analyzeTriangles(Arrays.asList(validTriangle, invalidTriangle));

        ArgumentCaptor<Map<TriangleType, Integer>> countsCaptor = ArgumentCaptor.forClass(Map.class);
        verify(resultWriter).writeResults(countsCaptor.capture(), any(), any());

        Map<TriangleType, Integer> actualCounts = countsCaptor.getValue();
        int expectedRectangular = 1;
        int expectedEquilateral = 0;
        int expectedIsosceles = 0;
        int expectedCalculateAreaCalls = 3;

        assertEquals(expectedRectangular, actualCounts.getOrDefault(TriangleType.RECTANGULAR, 0));
        assertEquals(expectedEquilateral, actualCounts.getOrDefault(TriangleType.EQUILATERAL, 0));
        assertEquals(expectedIsosceles, actualCounts.getOrDefault(TriangleType.ISOSCELES, 0));

        verify(triangleCalculationService, times(expectedCalculateAreaCalls)).calculateArea(any(Triangle.class));
    }

    @Test
    void determineType_ShouldReturnCorrectTypeForValidTriangle() throws TriangleException {
        Triangle triangle = new Triangle(1, 3, 4, 5);
        when(triangleValidator.isValid(triangle)).thenReturn(true);

        TriangleType actualType = triangleAnalysisService.determineType(triangle);
        TriangleType expectedType = TriangleType.RECTANGULAR;

        assertEquals(expectedType, actualType);
    }

    @Test
    void determineType_ShouldReturnNullForInvalidTriangle() throws TriangleException {
        Triangle triangle = new Triangle(1, 1, 1, 3);
        when(triangleValidator.isValid(triangle)).thenReturn(false);

        TriangleType actualType = triangleAnalysisService.determineType(triangle);
        TriangleType expectedType = null;

        assertEquals(expectedType, actualType);
    }

    @Test
    void determineType_ShouldThrowExceptionWhenValidationFails() {
        Triangle triangle = new Triangle(1, 3, 4, 5);
        when(triangleValidator.isValid(triangle)).thenThrow(new RuntimeException("Validation error"));

        Class<? extends Throwable> expectedException = RuntimeException.class;

        assertThrows(expectedException, () -> triangleAnalysisService.determineType(triangle));
    }

    @Test
    void analyzeTriangles_ShouldHandleEmptyList() throws TriangleException {
        triangleAnalysisService.analyzeTriangles(Collections.emptyList());

        ArgumentCaptor<Map<TriangleType, Integer>> countsCaptor = ArgumentCaptor.forClass(Map.class);
        verify(resultWriter).writeResults(countsCaptor.capture(), any(), any());

        Map<TriangleType, Integer> actualCounts = countsCaptor.getValue();
        int expectedRectangular = 0;
        int expectedEquilateral = 0;
        int expectedIsosceles = 0;

        assertEquals(expectedRectangular, actualCounts.getOrDefault(TriangleType.RECTANGULAR, 0));
        assertEquals(expectedEquilateral, actualCounts.getOrDefault(TriangleType.EQUILATERAL, 0));
        assertEquals(expectedIsosceles, actualCounts.getOrDefault(TriangleType.ISOSCELES, 0));

        verify(triangleValidator, never()).isValid(any());
        verify(triangleCalculationService, never()).calculateArea(any());
    }
}