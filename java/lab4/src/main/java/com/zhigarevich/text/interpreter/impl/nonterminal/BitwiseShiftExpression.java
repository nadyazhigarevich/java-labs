package com.zhigarevich.text.interpreter.impl.nonterminal;

import com.zhigarevich.text.interpreter.AbstractExpression;
import com.zhigarevich.text.interpreter.ExpressionContext;

public class BitwiseShiftExpression extends AbstractExpression {
    private final String operator;

    public BitwiseShiftExpression(String operator) {
        this.operator = operator;
    }

    @Override
    public void interpret(ExpressionContext context) {
        int right = context.popValue().intValue();
        int left = context.popValue().intValue();

        switch (operator) {
            case "<":
                context.pushValue((double) (left << right));
                break;
            case ">":
                context.pushValue((double) (left >> right));
                break;
            case "R": // Для >>> (заменяется на R при парсинге)
                context.pushValue((double) (left >>> right));
                break;
            default:
                throw new IllegalArgumentException("Unknown shift operator: " + operator);
        }
    }
}