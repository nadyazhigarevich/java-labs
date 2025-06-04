package com.zhigarevich.lab4.calculator.rpn;

import com.zhigarevich.lab4.exception.ApplicationException;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.zhigarevich.lab4.util.ExpressionUtil.ERROR_INVALID_CHARACTER;
import static com.zhigarevich.lab4.util.ExpressionUtil.ERROR_NULL_OR_EMPTY_EXPRESSION;
import static com.zhigarevich.lab4.util.ExpressionUtil.isNumber;

public class PostfixCalculator {

    public double calculate(String rpn) throws ApplicationException {
        if (rpn == null || rpn.trim().isEmpty()) {
            throw new ApplicationException(ERROR_NULL_OR_EMPTY_EXPRESSION);
        }

        Deque<Double> stack = new ArrayDeque<>();
        String[] tokens = rpn.split("\\s+");

        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (Operation.isOperator(token.charAt(0))) {
                performOperation(stack, token.charAt(0));
            } else {
                throw new ApplicationException(ERROR_INVALID_CHARACTER + token);
            }
        }

        if (stack.size() != 1) {
            throw new ApplicationException("Invalid RPN expression - stack has " + stack.size() + " elements.");
        }

        return stack.pop();
    }

    public void performOperation(Deque<Double> stack, char operator) throws ApplicationException {
        if (stack.size() < 2) {
            throw new ApplicationException("Not enough operands for operator: " + operator);
        }

        double b = stack.pop();
        double a = stack.pop();
        double result = Operation.fromSymbol(operator).execute(a, b);
        stack.push(result);
    }
}