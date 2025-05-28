package com.zhigarevich.text.interpreter.polish;

import java.util.*;

public class PolishNotationParser {
    private static final String OPERATORS = "+-*/&|^~<>R";
    private static final String DELIMITERS = "() " + OPERATORS;

    private static final String LEFT_PARENTHESIS = "(";
    private static final String RIGHT_PARENTHESIS = ")";

    private static final String SPACE = " ";
    private static final String UNARY_MINUS_REPLACEMENT = "~";
    private static final String MINUS = "-";

    private static final int PARENTHESIS_PRIORITY = 0;
    private static final int BITWISE_OR_PRIORITY = 1;
    private static final int BITWISE_XOR_PRIORITY = 2;
    private static final int BITWISE_AND_PRIORITY = 3;
    private static final int BITWISE_SHIFT_PRIORITY = 4;
    private static final int UNARY_OPERATOR_PRIORITY = 5;
    private static final int ADDITIVE_OPERATOR_PRIORITY = 6;
    private static final int MULTIPLICATIVE_OPERATOR_PRIORITY = 7;
    private static final int DEFAULT_PRIORITY = -1;

    public List<String> parse(String infix) {
        List<String> postfix = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        StringTokenizer tokenizer = new StringTokenizer(infix, DELIMITERS, true);
        String prev = "";
        String curr;

        while (tokenizer.hasMoreTokens()) {
            curr = tokenizer.nextToken();

            if (curr.equals(SPACE)) continue;

            if (isDelimiter(curr)) {
                if (curr.equals(LEFT_PARENTHESIS)) {
                    stack.push(curr);
                } else if (curr.equals(RIGHT_PARENTHESIS)) {
                    while (!stack.peek().equals(LEFT_PARENTHESIS)) {
                        postfix.add(stack.pop());
                        if (stack.isEmpty()) {
                            throw new IllegalArgumentException("Unmatched parentheses");
                        }
                    }
                    stack.pop();
                } else {
                    if (curr.equals(MINUS) && (prev.isEmpty() || (isDelimiter(prev) && !prev.equals(RIGHT_PARENTHESIS)))) {
                        curr = UNARY_MINUS_REPLACEMENT;
                    }

                    while (!stack.isEmpty() && priority(curr) <= priority(stack.peek())) {
                        postfix.add(stack.pop());
                    }
                    stack.push(curr);
                }
            } else {
                postfix.add(curr);
            }
            prev = curr;
        }

        while (!stack.isEmpty()) {
            if (stack.peek().equals(LEFT_PARENTHESIS)) {
                throw new IllegalArgumentException("Unmatched parentheses");
            }
            postfix.add(stack.pop());
        }

        return postfix;
    }

    private boolean isDelimiter(String token) {
        return token.length() == 1 && DELIMITERS.contains(token);
    }

    private int priority(String operator) {
        switch (operator) {
            case LEFT_PARENTHESIS:
            case RIGHT_PARENTHESIS:
                return PARENTHESIS_PRIORITY;
            case "|":
                return BITWISE_OR_PRIORITY;
            case "^":
                return BITWISE_XOR_PRIORITY;
            case "&":
                return BITWISE_AND_PRIORITY;
            case "<":
            case ">":
            case "R":
                return BITWISE_SHIFT_PRIORITY;
            case "~":
                return UNARY_OPERATOR_PRIORITY;
            case "+":
            case MINUS:
                return ADDITIVE_OPERATOR_PRIORITY;
            case "*":
            case "/":
                return MULTIPLICATIVE_OPERATOR_PRIORITY;
            default:
                return DEFAULT_PRIORITY;
        }
    }
}