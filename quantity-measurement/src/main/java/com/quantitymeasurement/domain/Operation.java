package com.quantitymeasurement.domain;

import com.quantitymeasurement.exception.QuantityException;

import java.util.function.DoubleBinaryOperator;

/**
 * UC13: Centralised arithmetic logic via lambda expressions and functional interfaces.
 * All four operations route through the same DoubleBinaryOperator, eliminating
 * duplicated arithmetic code in Quantity (DRY principle).
 */
public enum Operation {

    ADD((a, b) -> a + b),

    SUBTRACT((a, b) -> a - b),

    MULTIPLY((a, b) -> a * b),

    DIVIDE((a, b) -> {
        if (b == 0.0) throw new QuantityException("Division by zero is not allowed");
        return a / b;
    });

    private final DoubleBinaryOperator operator;

    Operation(DoubleBinaryOperator operator) {
        this.operator = operator;
    }

    public double apply(double a, double b) {
        return operator.applyAsDouble(a, b);
    }
}
