package com.quantitymeasurement.exception;

/**
 * Domain exception for invalid operations (category mismatch, unsupported arithmetic,
 * null units, division by zero, etc.).
 */
public class QuantityException extends RuntimeException {

    public QuantityException(String message) {
        super(message);
    }

    public QuantityException(String message, Throwable cause) {
        super(message, cause);
    }
}
