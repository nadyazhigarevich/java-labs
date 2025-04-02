package com.zhigarevich.triangle;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.service.impl.TriangleCalculationServiceImpl;
import com.zhigarevich.triangle.validator.TriangleValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TriangleCalculationServiceImplTest {
    private TriangleValidator validator;
    private TriangleCalculationServiceImpl service;

    @BeforeEach
    void setUp() {
        validator = mock(TriangleValidator.class);
        service = new TriangleCalculationServiceImpl(validator);
    }

    @Test
    void calculateArea_ShouldReturnCorrectValue() {
        Triangle triangle = new Triangle(1, 3.0, 4.0, 5.0);
        when(validator.isValid(triangle)).thenReturn(true);

        double expected = 6.0;
        double actual = service.calculateArea(triangle);

        assertEquals(expected, actual, 0.001);
        verify(validator).isValid(triangle);
    }

    @Test
    void calculatePerimeter_ShouldReturnCorrectValue() {
        Triangle triangle = new Triangle(1, 3.0, 4.0, 5.0);
        when(validator.isValid(triangle)).thenReturn(true);

        double expected = 12.0;
        double actual = service.calculatePerimeter(triangle);

        assertEquals(expected, actual, 0.001);
        verify(validator).isValid(triangle);
    }

    @Test
    void calculateArea_ShouldReturnNaNForInvalidTriangle() {
        Triangle triangle = new Triangle(1, 1.0, 2.0, 3.0);
        when(validator.isValid(triangle)).thenReturn(false);

        double result = service.calculateArea(triangle);

        assertTrue(Double.isNaN(result));
        verify(validator).isValid(triangle);
    }

    @Test
    void calculatePerimeter_ShouldReturnNaNForInvalidTriangle() {
        Triangle triangle = new Triangle(1, 1.0, 2.0, 3.0);
        when(validator.isValid(triangle)).thenReturn(false);

        double result = service.calculatePerimeter(triangle);

        assertTrue(Double.isNaN(result));
        verify(validator).isValid(triangle);
    }

    @Test
    void shouldHandleNullValidator() {
        TriangleCalculationServiceImpl nullValidatorService =
                new TriangleCalculationServiceImpl(null);
        Triangle triangle = new Triangle(1, 2.0, 2.0, 2.0);

        double areaResult = nullValidatorService.calculateArea(triangle);
        double perimeterResult = nullValidatorService.calculatePerimeter(triangle);

        assertTrue(Double.isNaN(areaResult));
        assertTrue(Double.isNaN(perimeterResult));
    }
}