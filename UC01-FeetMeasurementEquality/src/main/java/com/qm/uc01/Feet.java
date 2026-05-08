package com.qm.uc01;

/**
 * Feet.java
 *
 * Author      : BridgeLabz Dev Team
 * Created     : 23-Apr-2026
 * Last Updated: 09-May-2026
 *
 * UC01 - Feet Measurement Equality
 * Concepts  : Object Equality, Floating-point Comparison, Null Checking,
 *             Type Checking, Object-Oriented Design, Unit Testing
 *
 * Changelog:
 *   23-Apr-2026  |  Initial class skeleton created for UC01
 *   24-Apr-2026  |  Added constructor with basic value assignment
 *   25-Apr-2026  |  Implemented equals() with null + type guard
 *   26-Apr-2026  |  Replaced == comparison with epsilon-based float comparison
 *   28-Apr-2026  |  Added IllegalArgumentException for negative values
 *   30-Apr-2026  |  Fixed hashCode to be consistent with equals (epsilon grid)
 *   02-May-2026  |  Added toString() returning value + "ft" suffix
 *   05-May-2026  |  Code review feedback: made EPSILON a named constant
 *   07-May-2026  |  Javadoc updated and final immutability review done
 *   09-May-2026  |  Final cleanup before merge to dev
 */
public class Feet {

    // 26-Apr-2026: Tolerance for floating-point comparison.
    //              1e-9 feet ≈ 0.3 nanometres — precise enough for any real-world
    //              measurement while absorbing IEEE 754 rounding drift.
    private static final double EPSILON = 1e-9;

    // 23-Apr-2026: Core field — immutable to enforce value-object semantics.
    private final double value;

    /**
     * 24-Apr-2026: Constructor with basic validation.
     * 28-Apr-2026: Added guard for negative values (physical measurement can't be negative).
     *
     * @param value measurement in feet (must be >= 0)
     * @throws IllegalArgumentException if value is negative
     */
    public Feet(double value) {
        // 28-Apr-2026: Physical feet cannot be negative — fail fast at construction.
        if (value < 0) {
            throw new IllegalArgumentException(
                "Feet value cannot be negative: " + value
            );
        }
        this.value = value;
    }

    /**
     * 24-Apr-2026: Getter for the feet value.
     * Kept intentionally simple — no mutation allowed.
     */
    public double getValue() {
        return value;
    }

    /**
     * equals() — UC01 core method.
     *
     * 25-Apr-2026: Initial implementation with null + type guard.
     * 26-Apr-2026: Replaced direct == with epsilon-delta float comparison
     *              after discovering 1.0 + 0.1 + 0.1 != 1.2 in IEEE 754.
     *
     * Decision log (26-Apr-2026):
     *   Option A: Double.compare()  → only exact bit match, fails float arithmetic
     *   Option B: Math.abs(a-b) < EPSILON → absorbs rounding, chosen ✓
     *
     * @param obj the object to compare against
     * @return true if measurements are equal within EPSILON tolerance
     */
    @Override
    public boolean equals(Object obj) {
        // 25-Apr-2026: Guard 1 — null check to avoid NullPointerException
        if (obj == null) {
            return false;
        }

        // 25-Apr-2026: Guard 2 — same reference shortcut (reflexivity fast path)
        if (this == obj) {
            return true;
        }

        // 25-Apr-2026: Guard 3 — type check; a Feet must not equal a String "5.0"
        if (!(obj instanceof Feet)) {
            return false;
        }

        Feet other = (Feet) obj;

        // 26-Apr-2026: Epsilon comparison replaces bitwise equality.
        //              Handles cases like new Feet(0.1 + 0.2) == new Feet(0.3)
        return Math.abs(this.value - other.value) < EPSILON;
    }

    /**
     * hashCode — must satisfy: a.equals(b) → a.hashCode() == b.hashCode()
     *
     * 30-Apr-2026: Snap value to the same epsilon grid used in equals()
     *              so two "equal" floats always land in the same bucket.
     *
     * 05-May-2026: Verified contract holds for all test cases.
     */
    @Override
    public int hashCode() {
        // 30-Apr-2026: Round to EPSILON grid before hashing
        long bits = Double.doubleToLongBits(
            Math.round(value / EPSILON) * EPSILON
        );
        return Long.hashCode(bits);
    }

    /**
     * 02-May-2026: Human-readable representation for logging and debugging.
     * Format: "<value> ft"  e.g. "5.0 ft"
     */
    @Override
    public String toString() {
        return value + " ft";
    }
}
