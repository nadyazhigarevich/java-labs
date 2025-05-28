package com.zhigarevich.text.interpreter.impl.nonterminal;

import com.zhigarevich.text.interpreter.AbstractExpression;
import com.zhigarevich.text.interpreter.ExpressionContext;

public class BitwiseNotExpression extends AbstractExpression {
    @Override
    public void interpret(ExpressionContext context) {
        double value = context.popValue();
        context.pushValue((double) (~(int) value));
    }
}