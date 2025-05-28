package com.zhigarevich.text.interpreter.impl;

import com.zhigarevich.text.interpreter.ExpressionInterpreter;
import com.zhigarevich.text.interpreter.polish.PolishNotationCalculator;
import com.zhigarevich.text.interpreter.polish.PolishNotationParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

public class ArithmeticInterpreterImpl implements ExpressionInterpreter {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String interpret(String expression) {
        try {
            PolishNotationParser parser = new PolishNotationParser();
            PolishNotationCalculator calculator = new PolishNotationCalculator();
            List<String> polish = parser.parse(expression);
            double result = calculator.calculate(polish);
            return String.format("%.2f", result);
        } catch (Exception e) {
            logger.error("Error evaluating expression: " + expression, e);
            return expression;
        }
    }
}