package com.zhigarevich.text.interpreter.impl;

import com.zhigarevich.text.interpreter.ExpressionInterpreter;
import com.zhigarevich.text.interpreter.polish.PolishNotationCalculator;
import com.zhigarevich.text.interpreter.polish.PolishNotationParser;

import java.util.List;

public class ArithmeticInterpreterImpl implements ExpressionInterpreter {
    @Override
    public String interpret(String expression) {
        try {
            PolishNotationParser parser = new PolishNotationParser();
            List<String> polishNotation = parser.parse(expression);

            PolishNotationCalculator calculator = new PolishNotationCalculator();
            double result = calculator.calculate(polishNotation);

            return String.valueOf(result);
        } catch (Exception e) {
            return expression; // Возвращаем исходное выражение при ошибке
        }
    }
}