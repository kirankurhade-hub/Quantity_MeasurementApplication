package com.qm.uc01;

/**
 * Immutable value object representing a measurement in feet.
 *
 * Concepts: Object Equality, Floating-point Comparison, Null Checking,
 *           Type Checking, Object-Oriented Design
 */
public class Feet {

    /** Tolerance for floating-point comparison (1e-9 feet ≈ 0.3 nanometres). */
    private static final double EPSILON = 1e-9;

    private final double value;

    public Feet(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("Feet value cannot be negative: " + value);
        }
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    /**
     * Compares this Feet object to another for measurement equality.
     *
     * Handles:
     *  - null reference   → false  (null checking)
     *  - wrong type       → false  (type checking)
     *  - floating-point   → epsilon-based comparison
     */
    @Override
    public boolean equals(Object obj) {
        // null check
        if (obj == null) {
            return false;
        }
        // same reference shortcut
        if (this == obj) {
            return true;
        }
        // type check
        if (!(obj instanceof Feet)) {
            return false;
        }
        Feet other = (Feet) obj;
        // floating-point comparison using epsilon
        return Math.abs(this.value - other.value) < EPSILON;
    }

    @Override
    public int hashCode() {
        // Round to epsilon grid so equal objects share the same hash
        long bits = Double.doubleToLongBits(Math.round(value / EPSILON) * EPSILON);
        return Long.hashCode(bits);
    }

    @Override
    public String toString() {
        return value + " ft";
    }
}
