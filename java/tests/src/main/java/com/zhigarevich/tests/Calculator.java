package com.zhigarevich.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Calculator {
    private static final Logger logger = LogManager.getLogger(Calculator.class);

    public float performOperetion(float a, float b, char operation) {
        float result;
        switch (operation) {
            case '+':
                result = a + b;
                logger.info("Addition: {} + {} = {}", a, b, result);
                return result;
            case '-':
                result = a - b;
                logger.info("Subtraction: {} - {} = {}", a, b, result);
                return result;
            case '*':
                result = a * b;
                logger.info("Multiplication: {} * {} = {}", a, b, result);
                return result;
            case '/':
                if (b != 0) {
                    result = a / b;
                    logger.info("Division: {} / {} = {}", a, b, result);
                    return result;
                } else {
                    logger.warn("Division by zero attempted with {} / {}", a, b);
                    return 0;
                }
            case 'q':
                logger.info("Calculator exited.");
                System.exit(0);
            default:
                logger.error("Invalid operation: {}", operation);
                return 0;
        }
    }
}