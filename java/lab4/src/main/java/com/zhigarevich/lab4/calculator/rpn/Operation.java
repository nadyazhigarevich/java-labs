package com.zhigarevich.lab4.calculator.rpn;

import com.zhigarevich.lab4.exception.ApplicationException;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Enum representing mathematical operations with associated symbols, priorities, and behaviors.
 */
public enum Operation {

    ADD('+', 1, Double::sum),
    SUBTRACT('-', 1, (a, b) -> a - b),
    MULTIPLY('*', 2, (a, b) -> a * b),
    DIVIDE('/', 2, (a, b) -> {
        if (b == 0) {
            throw new ArithmeticException("Division by zero!");
        }
        return a / b;
    });

    private static final Map<Character, Operation> OPERATOR_MAP = EnumSet.allOf(Operation.class)
            .stream()
            .collect(Collectors.toMap(Operation::getSymbol, Function.identity()));
    private static final Set<Character> OPERATOR_SYMBOLS = OPERATOR_MAP.keySet();

    private final char symbol;
    private final int priority;
    private final BinaryOperator<Double> behavior;

    Operation(final char symbol, final int priority, final BinaryOperator<Double> behavior) {
        this.symbol = symbol;
        this.priority = priority;
        this.behavior = behavior;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getPriority() {
        return priority;
    }

    public double execute(final double a, final double b) {
        return behavior.apply(a, b);
    }

    public static Operation fromSymbol(final char symbol) throws ApplicationException {
        Operation op = OPERATOR_MAP.get(symbol);
        if (op == null) {
            throw new ApplicationException("Unknown operator: '" + symbol + "'");
        }
        return op;
    }

    public static boolean isOperator(final char symbol) {
        return OPERATOR_SYMBOLS.contains(symbol);
    }

    public static boolean hasPrecedence(char top, char current) throws ApplicationException {
        return comparePrecedence(fromSymbol(top), fromSymbol(current)) > 0;
    }

    private static int comparePrecedence(Operation op1, Operation op2) {
        return Integer.compare(op1.getPriority(), op2.getPriority());
    }
}