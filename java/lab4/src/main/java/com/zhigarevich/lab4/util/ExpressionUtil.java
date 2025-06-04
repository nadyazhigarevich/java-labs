package com.zhigarevich.lab4.util;

import com.zhigarevich.lab4.calculator.rpn.Operation;

public final class ExpressionUtil {

    public static final char LEFT_PARENTHESIS = '(';
    public static final char RIGHT_PARENTHESIS = ')';
    public static final char DECIMAL_POINT = '.';
    public static final char UNARY_MINUS = '-';
    public static final char SPACE_CHARACTER = ' ';

    public static final String ERROR_NULL_OR_EMPTY_EXPRESSION = "Expression cannot be null or empty.";
    public static final String ERROR_INVALID_CHARACTER = "Invalid character in expression: ";
    public static final String ERROR_MISMATCHED_PARENTHESES = "Mismatched parentheses in expression.";

    public static boolean isDigitOrDecimal(char ch) {
        return Character.isDigit(ch) || ch == DECIMAL_POINT;
    }

    public static boolean isUnaryMinus(char ch, boolean expectOperand) {
        return ch == UNARY_MINUS && expectOperand;
    }

    public static boolean isValidToken(char ch) {
        return Character.isDigit(ch) || Character.isWhitespace(ch) ||
                ch == LEFT_PARENTHESIS ||
                ch == RIGHT_PARENTHESIS ||
                ch == DECIMAL_POINT ||
                ch == UNARY_MINUS ||
                Operation.isOperator(ch);
    }

    public static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}