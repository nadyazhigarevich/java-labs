package com.zhigarevich.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        CalculatorController controller = new CalculatorController();
        try (Scanner scan = new Scanner(System.in)) {
            Calculator calculator = new Calculator();
            while (true) {
                float a = controller.getNumber(scan, "Enter the first number: ");
                float b = controller.getNumber(scan, "Enter the second number: ");
                logger.info("Entered numbers: a = {}, b = {}", a, b);

                logger.info("Enter the operation (+, -, /, *): ");
                char operation = scan.next().charAt(0);

                float result = calculator.performOperetion(a, b, operation);
                logger.info("Result of operation {} on numbers {} and {}: {}", operation, a, b, result);
            }
        }
    }
}