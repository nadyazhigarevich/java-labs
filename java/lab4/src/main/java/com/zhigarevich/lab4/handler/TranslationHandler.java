package com.zhigarevich.lab4.handler;

import com.zhigarevich.lab4.calculator.ArithmeticExpression;
import com.zhigarevich.lab4.calculator.Expression;
import com.zhigarevich.lab4.calculator.ExpressionCalculator;
import com.zhigarevich.lab4.exception.ApplicationException;
import com.zhigarevich.lab4.model.contract.TextComponent;

public class TranslationHandler extends AbstractTextHandler {

    public static final String SPACE_REGEX = " ";
    private final Expression interpreter = new ArithmeticExpression();
    private final ExpressionCalculator calculator = new ExpressionCalculator();

    @Override
    public TextComponent handle(String content) throws ApplicationException {
        String[] words = content.split(SPACE_REGEX);
        StringBuilder text = new StringBuilder();
        for (String word : words) {
            String expression = interpreter.interpret(word);
            text.append(
                    expression.isBlank() ? word
                            : String.valueOf(calculator.calculate(expression))
            ).append(SPACE_REGEX);
        }
        return this.nextHandler.handle(text.toString());
    }
}
