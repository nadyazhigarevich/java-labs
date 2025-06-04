package com.zhigarevich.lab4.calculator.rpn;

import com.zhigarevich.lab4.exception.ApplicationException;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.zhigarevich.lab4.util.ExpressionUtil.DECIMAL_POINT;
import static com.zhigarevich.lab4.util.ExpressionUtil.ERROR_INVALID_CHARACTER;
import static com.zhigarevich.lab4.util.ExpressionUtil.ERROR_MISMATCHED_PARENTHESES;
import static com.zhigarevich.lab4.util.ExpressionUtil.ERROR_NULL_OR_EMPTY_EXPRESSION;
import static com.zhigarevich.lab4.util.ExpressionUtil.LEFT_PARENTHESIS;
import static com.zhigarevich.lab4.util.ExpressionUtil.RIGHT_PARENTHESIS;
import static com.zhigarevich.lab4.util.ExpressionUtil.SPACE_CHARACTER;
import static com.zhigarevich.lab4.util.ExpressionUtil.UNARY_MINUS;
import static com.zhigarevich.lab4.util.ExpressionUtil.isDigitOrDecimal;
import static com.zhigarevich.lab4.util.ExpressionUtil.isUnaryMinus;
import static com.zhigarevich.lab4.util.ExpressionUtil.isValidToken;

public class InfixToRPNConverter {

    public String convertToRPN(String expression) throws ApplicationException {
        if (expression == null || expression.trim().isEmpty()) {
            throw new ApplicationException(ERROR_NULL_OR_EMPTY_EXPRESSION);
        }

        StringBuilder output = new StringBuilder();
        Deque<Character> operatorStack = new ArrayDeque<>();
        boolean expectingOperand = true;

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isWhitespace(ch)) {
                continue;
            }

            if (!isValidToken(ch)) {
                throw new ApplicationException(ERROR_INVALID_CHARACTER + ch);
            }

            if (isDigitOrDecimal(ch)) {
                i = processNumber(expression, i, output);
                expectingOperand = false;
            } else if (isUnaryMinus(ch, expectingOperand)) {
                i = processUnaryNumber(expression, i, output);
                expectingOperand = false;
            } else if (Operation.isOperator(ch)) {
                handleOperator(ch, operatorStack, output);
                expectingOperand = true;
            } else if (ch == LEFT_PARENTHESIS) {
                operatorStack.push(ch);
                expectingOperand = true;
            } else if (ch == RIGHT_PARENTHESIS) {
                handleClosingParenthesis(operatorStack, output);
                expectingOperand = false;
            }
        }

        flushOperators(operatorStack, output);
        return output.toString().trim();
    }

    /**
     * Processes a number (digits and optional decimal point) starting at the given index.
     */
    public int processNumber(String expression, int index, StringBuilder output) throws ApplicationException {
        int start = index;
        boolean hasDecimal = false;
        while (index < expression.length()) {
            char current = expression.charAt(index);
            if (current == DECIMAL_POINT) {
                if (hasDecimal) {
                    throw new ApplicationException("Invalid number with multiple decimals: "
                            + expression.substring(start, index + 1));
                }
                hasDecimal = true;
            } else if (!Character.isDigit(current)) {
                break;
            }
            index++;
        }
        output.append(expression, start, index).append(SPACE_CHARACTER);
        return index - 1;
    }

    public int processUnaryNumber(String expression, int index, StringBuilder output) throws ApplicationException {
        output.append(UNARY_MINUS);

        // Skip the '-'
        index++;
        int start = index;
        boolean hasDecimal = false;
        while (index < expression.length()) {
            char current = expression.charAt(index);
            if (current == DECIMAL_POINT) {
                if (hasDecimal) {
                    throw new ApplicationException("Invalid number with multiple decimals: -"
                            + expression.substring(start, index + 1));
                }
                hasDecimal = true;
            } else if (!Character.isDigit(current)) {
                break;
            }
            index++;
        }
        if (index == start) {
            throw new ApplicationException("Invalid unary minus without subsequent number: -");
        }
        output.append(expression, start, index).append(SPACE_CHARACTER);
        return index - 1;
    }

    public void handleOperator(char operator, Deque<Character> operatorStack, StringBuilder output) throws ApplicationException {
        while (!operatorStack.isEmpty() && shouldPopOperator(operatorStack.peek(), operator)) {
            output.append(operatorStack.pop()).append(SPACE_CHARACTER);
        }
        operatorStack.push(operator);
    }

    private boolean shouldPopOperator(char stackTop, char currentOperator) throws ApplicationException {
        return Operation.isOperator(stackTop) && Operation.hasPrecedence(stackTop, currentOperator);
    }

    public void handleClosingParenthesis(Deque<Character> operatorStack, StringBuilder output) throws ApplicationException {
        while (!operatorStack.isEmpty() && operatorStack.peek() != LEFT_PARENTHESIS) {
            output.append(operatorStack.pop()).append(SPACE_CHARACTER);
        }

        if (operatorStack.isEmpty()) {
            throw new ApplicationException(ERROR_MISMATCHED_PARENTHESES);
        }

        operatorStack.pop();
    }

    public void flushOperators(Deque<Character> operatorStack, StringBuilder output) throws ApplicationException {
        while (!operatorStack.isEmpty()) {
            var top = operatorStack.pop();
            if (top == LEFT_PARENTHESIS) {
                throw new ApplicationException(ERROR_MISMATCHED_PARENTHESES);
            }
            output.append(top).append(SPACE_CHARACTER);
        }
    }
}