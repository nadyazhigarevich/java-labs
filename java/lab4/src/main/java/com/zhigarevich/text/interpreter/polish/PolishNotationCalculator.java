package com.zhigarevich.text.interpreter.polish;

import com.zhigarevich.text.interpreter.AbstractExpression;
import com.zhigarevich.text.interpreter.ExpressionContext;
import com.zhigarevich.text.interpreter.impl.nonterminal.*;
import com.zhigarevich.text.interpreter.impl.terminal.NumberExpression;

import java.util.List;

public class PolishNotationCalculator {
    private static final String ADD_OPERATOR = "+";
    private static final String SUBTRACT_OPERATOR = "-";
    private static final String MULTIPLY_OPERATOR = "*";
    private static final String DIVIDE_OPERATOR = "/";
    private static final String BITWISE_AND_OPERATOR = "&";
    private static final String BITWISE_OR_OPERATOR = "|";
    private static final String BITWISE_XOR_OPERATOR = "^";
    private static final String BITWISE_NOT_OPERATOR = "~";
    private static final String LEFT_SHIFT_OPERATOR = "<";
    private static final String RIGHT_SHIFT_OPERATOR = ">";
    private static final String ROTATE_OPERATOR = "R";

    public double calculate(List<String> postfix) {
        ExpressionContext context = new ExpressionContext();

        for (String token : postfix) {
            AbstractExpression expression = getExpression(token);
            expression.interpret(context);
        }

        return context.popValue();
    }

    private AbstractExpression getExpression(String token) {
        switch (token) {
            case ADD_OPERATOR:
                return new AddExpression();
            case SUBTRACT_OPERATOR:
                return new SubtractExpression();
            case MULTIPLY_OPERATOR:
                return new MultiplyExpression();
            case DIVIDE_OPERATOR:
                return new DivideExpression();
            case BITWISE_AND_OPERATOR:
                return new BitwiseAndExpression();
            case BITWISE_OR_OPERATOR:
                return new BitwiseOrExpression();
            case BITWISE_XOR_OPERATOR:
                return new BitwiseXorExpression();
            case BITWISE_NOT_OPERATOR:
                return new BitwiseNotExpression();
            case LEFT_SHIFT_OPERATOR:
            case RIGHT_SHIFT_OPERATOR:
            case ROTATE_OPERATOR:
                return new BitwiseShiftExpression(token);
            default:
                return new NumberExpression(Double.parseDouble(token));
        }
    }
}