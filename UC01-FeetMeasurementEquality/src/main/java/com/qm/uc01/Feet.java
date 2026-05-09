package com.qm.uc01;

/**
 * Represents an immutable measurement in feet.
 *
 * <p>Implements value-object semantics: two {@code Feet} instances
 * are considered equal if their measurements are within floating-point
 * tolerance of each other, rather than requiring exact bit equality.</p>
 *
 * @author BridgeLabz Dev Team
 * @since 23-Apr-2026
 */
public class Feet {

    /**
     * Tolerance used for floating-point comparison.
     * 1e-9 feet ≈ 0.3 nanometres — absorbs IEEE 754 rounding drift
     * without masking any real-world measurement difference.
     */
    private static final double EPSILON = 1e-9;

    private final double value;

    /**
     * Creates a new {@code Feet} measurement.
     *
     * @param value the measurement in feet; must be non-negative
     * @throws IllegalArgumentException if {@code value} is negative
     */
    public Feet(double value) {
        if (value < 0) {
            throw new IllegalArgumentException(
                "Feet value cannot be negative: " + value
            );
        }
        this.value = value;
    }

    /**
     * Returns the measurement value in feet.
     */
    public double getValue() {
        return value;
    }

    /**
     * Compares this measurement to {@code obj} for physical equality.
     *
     * <p>Two {@code Feet} objects are equal when their values differ by
     * less than {@link #EPSILON}. This handles cases such as
     * {@code 0.1 + 0.2} not producing an exact {@code 0.3} in IEEE 754.</p>
     *
     * @param obj the object to compare
     * @return {@code true} if both represent the same measurement
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (!(obj instanceof Feet)) return false;

        Feet other = (Feet) obj;
        return Math.abs(this.value - other.value) < EPSILON;
    }

    /**
     * Returns a hash consistent with {@link #equals}: values that compare
     * equal are snapped to the same epsilon grid before hashing.
     */
    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(
            Math.round(value / EPSILON) * EPSILON
        );
        return Long.hashCode(bits);
    }

    @Override
    public String toString() {
        return value + " ft";
    }
}
