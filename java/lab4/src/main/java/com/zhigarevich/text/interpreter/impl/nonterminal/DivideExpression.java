package com.zhigarevich.text.interpreter.impl.nonterminal;

import com.zhigarevich.text.interpreter.AbstractExpression;
import com.zhigarevich.text.interpreter.ExpressionContext;

public class DivideExpression extends AbstractExpression {
    @Override
    public void interpret(ExpressionContext context) {
        Double right = context.popValue();
        Double left = context.popValue();
        if (right == 0) {
            throw new ArithmeticException("Division by zero");
        }
        context.pushValue(left / right);
    }
}