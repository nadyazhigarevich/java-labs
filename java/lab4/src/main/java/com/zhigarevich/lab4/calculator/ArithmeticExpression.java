package com.zhigarevich.lab4.calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArithmeticExpression implements Expression {

    private static final Pattern EXPRESSION_PATTERN = Pattern.compile(
            "(?![a-zA-Z])(-?(?:\\d+(?:\\.\\d+)?|\\([^()]*(?:\\([^()]*\\)[^()]*)*\\))(?:\\s*[-+*/]\\s*-?(?:\\d+(?:\\.\\d+)?|\\([^()]*(?:\\([^()]*\\)[^()]*)*\\)))+)"
    );

    @Override
    public String interpret(String context) {
        StringBuilder expressions = new StringBuilder();
        Matcher matcher = EXPRESSION_PATTERN.matcher(context);

        while (matcher.find()) {
            expressions.append(matcher.group().trim());
        }
        return expressions.toString();
    }
}