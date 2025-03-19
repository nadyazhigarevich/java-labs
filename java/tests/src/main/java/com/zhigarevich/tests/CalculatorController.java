package com.zhigarevich.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class CalculatorController {
    private static final Logger logger = LogManager.getLogger(CalculatorController.class);

    public float getNumber(Scanner scan, String message) {
        boolean validInput = false;
        float number = 0;

        while (!validInput) {
            try {
                logger.info(message);
                number = scan.nextFloat();
                validInput = true;
            } catch (Exception e) {
                logger.error("Invalid input, please enter a valid number.");
                scan.next();
            }
        }
        return number;
    }
}