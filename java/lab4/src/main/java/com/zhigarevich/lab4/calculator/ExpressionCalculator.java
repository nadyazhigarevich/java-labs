package com.zhigarevich.lab4.calculator;

import com.zhigarevich.lab4.calculator.rpn.InfixToRPNConverter;
import com.zhigarevich.lab4.calculator.rpn.PostfixCalculator;
import com.zhigarevich.lab4.exception.ApplicationException;

public class ExpressionCalculator {

    private final InfixToRPNConverter converter = new InfixToRPNConverter();
    private final PostfixCalculator calculator = new PostfixCalculator();

    public double calculate(String expression) throws ApplicationException {
        try {
            String rpn = converter.convertToRPN(expression);

            return calculator.calculate(rpn);
        } catch (Exception e) {
            throw new ApplicationException("Error processing expression: " + expression, e);
        }
    }
}