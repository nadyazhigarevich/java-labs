package com.zhigarevich.text.interpreter.polish;

import java.util.*;

public class PolishNotationParser {
    private static final String OPERATORS = "+-*/&|^~<>R";
    private static final String DELIMITERS = "() " + OPERATORS;

    public List<String> parse(String infix) {
        List<String> postfix = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        StringTokenizer tokenizer = new StringTokenizer(infix, DELIMITERS, true);
        String prev = "";
        String curr;

        while (tokenizer.hasMoreTokens()) {
            curr = tokenizer.nextToken();

            if (curr.equals(" ")) continue;

            if (isDelimiter(curr)) {
                if (curr.equals("(")) {
                    stack.push(curr);
                } else if (curr.equals(")")) {
                    while (!stack.peek().equals("(")) {
                        postfix.add(stack.pop());
                        if (stack.isEmpty()) {
                            throw new IllegalArgumentException("Unmatched parentheses");
                        }
                    }
                    stack.pop();
                } else {
                    if (curr.equals("-") && (prev.equals("") || isDelimiter(prev) && !prev.equals(")"))) {
                        curr = "~";
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
            if (stack.peek().equals("(")) {
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
            case "(": return 0;
            case ")": return 0;
            case "|": return 1;
            case "^": return 2;
            case "&": return 3;
            case "<": case ">": case "R": return 4;
            case "~": return 5;
            case "+": case "-": return 6;
            case "*": case "/": return 7;
            default: return -1;
        }
    }
}