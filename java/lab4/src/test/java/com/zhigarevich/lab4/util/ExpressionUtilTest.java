package com.zhigarevich.lab4.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Expression Utility Tests")
class ExpressionUtilTest {

    @ParameterizedTest(name = "[{index}] Character ''{0}'' should be digit or decimal: {1}")
    @CsvSource({
            "'0', true",
            "'9', true",
            "'.', true",
            "'a', false",
            "' ', false",
            "'(', false"
    })
    @DisplayName("Check if character is digit or decimal")
    void isDigitOrDecimal_VariousCharacters_ReturnsExpected(char ch, boolean expected) {
        assertEquals(expected, ExpressionUtil.isDigitOrDecimal(ch));
    }

    @ParameterizedTest(name = "[{index}] Character ''{0}'' with expectOperand={1} should be unary minus: {2}")
    @CsvSource({
            "'-', true, true",
            "'-', false, false",
            "'+', true, false",
            "'3', true, false"
    })
    @DisplayName("Check if character is unary minus")
    void isUnaryMinus_VariousCases_ReturnsExpected(char ch, boolean expectOperand, boolean expected) {
        assertEquals(expected, ExpressionUtil.isUnaryMinus(ch, expectOperand));
    }

    @ParameterizedTest(name = "[{index}] Character ''{0}'' should be valid token: {1}")
    @CsvSource({
            "'0', true",    // Digit
            "'.', true",    // Decimal
            "' ', true",    // Whitespace
            "'(', true",    // Parenthesis
            "')', true",    // Parenthesis
            "'-', true",    // Operator
            "'+', true",    // Operator
            "'*', true",    // Operator
            "'/', true",    // Operator
            "'a', false",   // Invalid
            "'@', false"    // Invalid
    })
    @DisplayName("Check if character is valid token")
    void isValidToken_VariousCharacters_ReturnsExpected(char ch, boolean expected) {
        assertEquals(expected, ExpressionUtil.isValidToken(ch));
    }

    @ParameterizedTest(name = "[{index}] Token \"{0}\" should be valid number: {1}")
    @CsvSource({
            "'0', true",
            "'123', true",
            "'-123', true",
            "'12.34', true",
            "'.5', true",
            "'-.5', true",
            "'12.34.56', false",
            "'abc', false",
            "'', false",
            "' ', false"
    })
    @DisplayName("Check if string is valid number")
    void isNumber_VariousStrings_ReturnsExpected(String token, boolean expected) {
        assertEquals(expected, ExpressionUtil.isNumber(token));
    }

    @Test
    @DisplayName("Constants should have correct values")
    void constants_ShouldHaveCorrectValues() {
        assertAll(
                () -> assertEquals('(', ExpressionUtil.LEFT_PARENTHESIS),
                () -> assertEquals(')', ExpressionUtil.RIGHT_PARENTHESIS),
                () -> assertEquals('.', ExpressionUtil.DECIMAL_POINT),
                () -> assertEquals('-', ExpressionUtil.UNARY_MINUS),
                () -> assertEquals(' ', ExpressionUtil.SPACE_CHARACTER),
                () -> assertEquals("Expression cannot be null or empty.", 
                        ExpressionUtil.ERROR_NULL_OR_EMPTY_EXPRESSION),
                () -> assertEquals("Invalid character in expression: ", 
                        ExpressionUtil.ERROR_INVALID_CHARACTER),
                () -> assertEquals("Mismatched parentheses in expression.", 
                        ExpressionUtil.ERROR_MISMATCHED_PARENTHESES)
        );
    }

    @ParameterizedTest
    @ValueSource(chars = {'+', '-', '*', '/'})
    @DisplayName("Valid operators should be recognized as valid tokens")
    void isValidToken_ValidOperators_ReturnsTrue(char operator) {
        assertTrue(ExpressionUtil.isValidToken(operator));
    }
}