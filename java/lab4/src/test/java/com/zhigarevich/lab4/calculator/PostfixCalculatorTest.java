package com.zhigarevich.lab4.calculator;

import com.zhigarevich.lab4.calculator.rpn.PostfixCalculator;
import com.zhigarevich.lab4.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.zhigarevich.lab4.util.ExpressionUtil.ERROR_NULL_OR_EMPTY_EXPRESSION;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Postfix Calculator Tests")
class PostfixCalculatorTest {

    private PostfixCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new PostfixCalculator();
    }

    @ParameterizedTest(name = "[{index}] {0} => {1}")
    @CsvSource({
            "'3 4 +', 7",
            "'3 4 -', -1",
            "'3 4 *', 12",
            "'3 4 /', 0.75",
            "'5 1 2 + 4 * + 3 -', 14",
            "'2 3 4 * +', 14",
            "'3.5 4.2 +', 7.7",
            "'0 3 - 4 +', 1",      // -3 + 4
            "'3 0 4 - +', -1"      // 3 + -4
    })
    @DisplayName("Calculate valid RPN expressions")
    void calculate_ValidExpressions_ReturnsCorrectResult(String rpn, double expected) throws ApplicationException {
        var result = calculator.calculate(rpn);
        assertEquals(expected, result, 0.0001);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "3 4 + *",     // Invalid operator position
            "3 4",         // Too many operands
            "3 +",         // Not enough operands
            "3 4 ?",       // Invalid operator
            "3.4.5 2 +",   // Invalid number
            "abc 3 +"      // Invalid token
    })
    @DisplayName("Calculate invalid RPN expressions")
    void calculate_InvalidExpressions_ThrowsBusinessException(String invalidRpn) {
        var exception = assertThrows(ApplicationException.class,
                () -> calculator.calculate(invalidRpn));

        assertAll(
                () -> assertNotNull(exception.getMessage()),
                () -> assertFalse(exception.getMessage().isEmpty())
        );
    }

    @Test
    @DisplayName("Handle null/empty input")
    void calculate_NullOrEmpty_ThrowsBusinessException() {
        assertAll(
                () -> {
                    var exception = assertThrows(ApplicationException.class,
                            () -> calculator.calculate(null));
                    assertEquals(ERROR_NULL_OR_EMPTY_EXPRESSION, exception.getMessage());
                },
                () -> {
                    var exception = assertThrows(ApplicationException.class,
                            () -> calculator.calculate(""));
                    assertEquals(ERROR_NULL_OR_EMPTY_EXPRESSION, exception.getMessage());
                },
                () -> assertThrows(ApplicationException.class,
                        () -> calculator.calculate("   "))
        );
    }

    @Test
    @DisplayName("Perform operation with insufficient operands")
    void performOperation_InsufficientOperands_ThrowsException() {
        var stack = new java.util.ArrayDeque<Double>();
        stack.push(3.0);

        var exception = assertThrows(ApplicationException.class,
                () -> calculator.performOperation(stack, '+'));

        assertAll(
                () -> assertEquals("Not enough operands for operator: +", exception.getMessage()),
                () -> assertEquals(1, stack.size())
        );
    }

    @ParameterizedTest(name = "[{index}] {0} {1} {2} => {3}")
    @CsvSource({
            "5, 3, +, 8",
            "5, 3, -, -2",
            "5, 3, *, 15",
            "6, 3, /, 0.5",
            "5, 0, /, 0"
    })
    @DisplayName("Perform basic operations")
    void performOperation_ValidOperations_ReturnsCorrectResult(double a, double b, char op, double expected)
            throws ApplicationException {
        var stack = new java.util.ArrayDeque<Double>();
        stack.push(b);
        stack.push(a);

        calculator.performOperation(stack, op);

        assertAll(
                () -> assertEquals(1, stack.size()),
                () -> assertEquals(expected, stack.peek(), 0.0001)
        );
    }
}