package com.zhigarevich.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private static final Logger logger = LogManager.getLogger(CalculatorTest.class);
    private final Calculator calculator = new Calculator();

    @Test
    void performOperation_Addition() {
        double expected = 5.0;
        double actual = calculator.performOperetion(2.0f, 3.0f, '+');
        logger.info("Test Addition: expected = {}, actual = {}", expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    void performOperation_Subtraction() {
        double expected = 1.0;
        double actual = calculator.performOperetion(3.0f, 2.0f, '-');
        logger.info("Test Subtraction: expected = {}, actual = {}", expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    void performOperation_Multiplication() {
        double expected = 6.0;
        double actual = calculator.performOperetion(2.0f, 3.0f, '*');
        logger.info("Test Multiplication: expected = {}, actual = {}", expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    void performOperation_Division() {
        double expected = 2.0;
        double actual = calculator.performOperetion(6.0f, 3.0f, '/');
        logger.info("Test Division: expected = {}, actual = {}", expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    void performOperation_DivisionByZero() {
        double expected = 0.0;
        double actual = calculator.performOperetion(6.0f, 0.0f, '/');
        logger.warn("Test Division by Zero: expected = {}, actual = {}", expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    void performOperation_InvalidOperation() {
        double expected = 0.0;
        double actual = calculator.performOperetion(6.0f, 3.0f, 'x');
        logger.error("Test Invalid Operation: expected = {}, actual = {}", expected, actual);
        assertEquals(expected, actual);
    }
}