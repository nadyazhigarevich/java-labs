package com.zhigarevich.text.interpreter.impl;

import com.zhigarevich.text.interpreter.ExpressionInterpreter;
import com.zhigarevich.text.interpreter.polish.PolishNotationCalculator;
import com.zhigarevich.text.interpreter.polish.PolishNotationParser;
import java.util.List;

public class BitwiseInterpreterImpl implements ExpressionInterpreter {
    @Override
    public String interpret(String expression) {
        try {
            PolishNotationParser parser = new PolishNotationParser();
            List<String> polishNotation = parser.parse(expression.replaceAll(">>>", "R")
                    .replaceAll("<<", "<")
                    .replaceAll(">>", ">"));

            PolishNotationCalculator calculator = new PolishNotationCalculator();
            double result = calculator.calculate(polishNotation);

            return String.valueOf((int) result);
        } catch (Exception e) {
            return expression;
        }
    }
}