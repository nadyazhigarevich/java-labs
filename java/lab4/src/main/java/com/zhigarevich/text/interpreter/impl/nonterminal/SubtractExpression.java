package com.zhigarevich.text.interpreter.impl.nonterminal;

import com.zhigarevich.text.interpreter.AbstractExpression;
import com.zhigarevich.text.interpreter.ExpressionContext;

public class SubtractExpression extends AbstractExpression {
    @Override
    public void interpret(ExpressionContext context) {
        Double right = context.popValue();
        Double left = context.popValue();
        context.pushValue(left - right);
    }
}