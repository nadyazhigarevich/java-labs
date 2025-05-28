package com.zhigarevich.text.interpreter.impl.nonterminal;

import com.zhigarevich.text.interpreter.AbstractExpression;
import com.zhigarevich.text.interpreter.ExpressionContext;

public class BitwiseOrExpression extends AbstractExpression {
    @Override
    public void interpret(ExpressionContext context) {
        int right = context.popValue().intValue();
        int left = context.popValue().intValue();
        context.pushValue((double) (left | right));
    }
}