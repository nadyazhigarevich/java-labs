package com.zhigarevich.text.interpreter.impl;

import com.zhigarevich.text.interpreter.ExpressionInterpreter;
import com.zhigarevich.text.interpreter.polish.PolishNotationCalculator;
import com.zhigarevich.text.interpreter.polish.PolishNotationParser;

import java.util.List;

public class BitwiseInterpreterImpl implements ExpressionInterpreter {
    private static final String TRIPLE_SHIFT_RIGHT = ">>>";
    private static final String TRIPLE_SHIFT_REPLACEMENT = "R";
    private static final String SHIFT_LEFT = "<<";
    private static final String SHIFT_RIGHT = ">>";
    private static final String SHIFT_LEFT_REPLACEMENT = "<";
    private static final String SHIFT_RIGHT_REPLACEMENT = ">";

    @Override
    public String interpret(String expression) {
        try {
            String normalizedExpression = normalizeBitwiseOperators(expression);
            PolishNotationParser parser = new PolishNotationParser();
            List<String> polishNotation = parser.parse(normalizedExpression);

            PolishNotationCalculator calculator = new PolishNotationCalculator();
            double result = calculator.calculate(polishNotation);

            return String.valueOf((int) result);
        } catch (Exception e) {
            return expression;
        }
    }

    private String normalizeBitwiseOperators(String expression) {
        return expression.replaceAll(TRIPLE_SHIFT_RIGHT, TRIPLE_SHIFT_REPLACEMENT)
                .replaceAll(SHIFT_LEFT, SHIFT_LEFT_REPLACEMENT)
                .replaceAll(SHIFT_RIGHT, SHIFT_RIGHT_REPLACEMENT);
    }
}