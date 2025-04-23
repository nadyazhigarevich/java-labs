package com.zhigarevich.triangle.exception;

public class TriangleValidationException extends Exception {
    public TriangleValidationException() {
    }

    public TriangleValidationException(String message) {
        super(message);
    }

    public TriangleValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TriangleValidationException(Throwable cause) {
        super(cause);
    }
}
