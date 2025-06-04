package com.zhigarevich.lab4.calculator;

import com.zhigarevich.lab4.calculator.rpn.InfixToRPNConverter;
import com.zhigarevich.lab4.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Infix to RPN Converter Tests")
class InfixToRPNConverterTest {

    private InfixToRPNConverter converter;

    @BeforeEach
    void setUp() {
        converter = new InfixToRPNConverter();
    }

    @ParameterizedTest(name = "[{index}] {0} => {1}")
    @CsvSource({
        "'3 + 4', '3 4 +'",
        "'3 * 4 + 5', '3 4 * 5 +'",
        "'(3 + 4) * 5', '3 4 + 5 *'",
        "'-3 + 4', '-3 4 +'",
        "'3.5 + 4.2', '3.5 4.2 +'",
        "'3 + 4 * 2 / (1 - 5)', '3 4 2 1 5 - / * +'"
    })
    @DisplayName("Convert valid infix to RPN")
    void convertToRPN_ValidExpressions_ReturnsCorrectRPN(String infix, String expectedRPN) throws ApplicationException {
        var result = converter.convertToRPN(infix);
        assertEquals(expectedRPN, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
         "(3 + 4", "3.4.5 + 2", "--3 + 4", "3 $ 4"
    })
    @DisplayName("Convert invalid infix expressions")
    void convertToRPN_InvalidExpressions_ThrowsBusinessException(String invalidExpression) {
        assertThrows(ApplicationException.class, () -> converter.convertToRPN(invalidExpression));
    }

    @Test
    @DisplayName("Handle null/empty input")
    void convertToRPN_NullOrEmpty_ThrowsBusinessException() {
        assertAll(
            () -> assertThrows(ApplicationException.class, () -> converter.convertToRPN(null)),
            () -> assertThrows(ApplicationException.class, () -> converter.convertToRPN("")),
            () -> assertThrows(ApplicationException.class, () -> converter.convertToRPN("   "))
        );
    }

    @Test
    @DisplayName("Process valid numbers")
    void processNumber_ValidNumbers_AppendsCorrectly() throws ApplicationException {
        var output = new StringBuilder();
        var expression = "123.45 + 67";

        var newIndex = converter.processNumber(expression, 0, output);

        assertAll(
            () -> assertEquals(5, newIndex),
            () -> assertEquals("123.45 ", output.toString())
        );
    }

    @Test
    @DisplayName("Process invalid numbers")
    void processNumber_InvalidNumbers_ThrowsException() {
        var output = new StringBuilder();
        var expression = "12.3.45";
        
        var exception = assertThrows(ApplicationException.class,
            () -> converter.processNumber(expression, 0, output));
        
        assertAll(
            () -> assertTrue(exception.getMessage().contains("Invalid number")),
            () -> assertTrue(output.toString().isEmpty())
        );
    }

    @Test
    @DisplayName("Process unary numbers")
    void processUnaryNumber_ValidCases_AppendsCorrectly() throws ApplicationException {
        var output = new StringBuilder();
        var expression = "-123.45 + 67";

        var newIndex = converter.processUnaryNumber(expression, 0, output);

        assertAll(
            () -> assertEquals(6, newIndex),
            () -> assertEquals("-123.45 ", output.toString())
        );
    }

    @Test
    @DisplayName("Handle operator precedence")
    void handleOperator_PrecedenceRules() throws ApplicationException {
        var stack = new ArrayDeque<Character>();
        stack.push('*');
        var output = new StringBuilder();

        converter.handleOperator('+', stack, output);

        assertAll(
            () -> assertEquals("* ", output.toString()),
            () -> assertEquals('+', stack.peek())
        );
    }

    @Test
    @DisplayName("Handle parentheses")
    void handleClosingParenthesis_BalancedCases() throws ApplicationException {
        var stack = new ArrayDeque<Character>();
        stack.push('+');
        stack.push('(');
        var output = new StringBuilder();

        converter.handleClosingParenthesis(stack, output);

        assertAll(
            () -> assertEquals("", output.toString()),
            () -> assertFalse(stack.isEmpty())
        );
    }

    @Test
    @DisplayName("Flush remaining operators")
    void flushOperators_WithValidStack() throws ApplicationException {
        var stack = new ArrayDeque<Character>();
        stack.push('+');
        stack.push('*');
        var output = new StringBuilder();

        converter.flushOperators(stack, output);

        assertAll(
            () -> assertEquals("* + ", output.toString()),
            () -> assertTrue(stack.isEmpty())
        );
    }
}