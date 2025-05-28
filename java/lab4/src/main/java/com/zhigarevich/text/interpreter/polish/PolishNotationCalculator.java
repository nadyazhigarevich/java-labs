package com.zhigarevich.text.interpreter.polish;

import com.zhigarevich.text.interpreter.AbstractExpression;
import com.zhigarevich.text.interpreter.impl.nonterminal.*;
import com.zhigarevich.text.interpreter.impl.terminal.NumberExpression;
import com.zhigarevich.text.interpreter.ExpressionContext;
import java.util.List;

public class PolishNotationCalculator {
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
            case "+": return new AddExpression();
            case "-": return new SubtractExpression();
            case "*": return new MultiplyExpression();
            case "/": return new DivideExpression();
            case "&": return new BitwiseAndExpression();
            case "|": return new BitwiseOrExpression();
            case "^": return new BitwiseXorExpression();
            case "~": return new BitwiseNotExpression();
            case "<": case ">": case "R": return new BitwiseShiftExpression(token);
            default: return new NumberExpression(Double.parseDouble(token));
        }
    }
}