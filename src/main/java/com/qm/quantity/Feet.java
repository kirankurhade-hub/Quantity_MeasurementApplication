package com.qm.quantity;

import java.util.Objects;

/**
 * Value object representing a measurement in feet.
 */
public final class Feet {
    private static final double EPSILON = 1e-9;
    private final double value;

    public Feet(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Feet otherFeet)) {
            return false;
        }
        return Math.abs(this.value - otherFeet.value) < EPSILON;
    }

    @Override
    public int hashCode() {
        long normalized = Math.round(value / EPSILON);
        return Objects.hash(normalized);
    }
}
