package com.zhigarevich.text.interpreter.impl.terminal;

import com.zhigarevich.text.interpreter.AbstractExpression;
import com.zhigarevich.text.interpreter.ExpressionContext;

public class NumberExpression extends AbstractExpression {
    private Double number;

    public NumberExpression(Double number) {
        this.number = number;
    }

    @Override
    public void interpret(ExpressionContext context) {
        context.pushValue(number);
    }
}