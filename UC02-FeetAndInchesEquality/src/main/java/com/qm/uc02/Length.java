package com.qm.uc02;

/**
 * Length.java
 *
 * Author      : BridgeLabz Dev Team
 * Created     : 30-Apr-2026
 * Last Updated: 09-May-2026
 *
 * UC02 - Feet and Inches Measurement Equality
 * Concept   : Object Encapsulation
 *
 * Design goal (30-Apr-2026):
 *   UC01 only handled feet as a single double.
 *   UC02 encapsulates BOTH feet and inches inside one value object — Length.
 *   The caller works with a single object; internal normalization is hidden.
 *
 * Encapsulation decisions:
 *   - feet and inches are private final (immutable after construction)
 *   - conversion to a single base unit (total inches) is a private method
 *   - external code never needs to know how equality is computed internally
 *
 * Changelog:
 *   30-Apr-2026  |  Class skeleton created — two fields: feet, inches
 *   01-May-2026  |  Added toTotalInches() private helper for normalisation
 *   01-May-2026  |  Implemented equals() comparing normalised inch values
 *   02-May-2026  |  Added null, self, and type guards in equals()
 *   03-May-2026  |  hashCode() implemented on normalised total inches
 *   04-May-2026  |  Validation: feet >= 0, inches in [0, 11]
 *   05-May-2026  |  toString() returns "X ft Y in" format
 *   07-May-2026  |  Code review: added Javadoc for all public methods
 *   08-May-2026  |  Refactor: INCHES_PER_FOOT extracted as named constant
 *   09-May-2026  |  Final cleanup and merge to dev
 */
public class Length {

    // 08-May-2026: Named constant — avoids magic number 12 scattered across methods.
    //              1 foot = 12 inches (exact, international definition).
    private static final int INCHES_PER_FOOT = 12;

    // 01-May-2026: Tolerance for floating-point comparison of normalised inches.
    //              Mirrors UC01 EPSILON — consistent precision policy across all UCs.
    private static final double EPSILON = 1e-9;

    // 30-Apr-2026: Core encapsulated fields — private and final (immutable).
    //              External callers cannot directly access or modify these.
    private final double feet;
    private final double inches;

    /**
     * 30-Apr-2026: Primary constructor — accepts feet and inches separately.
     * 04-May-2026: Validation added after review:
     *              - feet cannot be negative (no negative physical measurement)
     *              - inches must be in [0.0, 11.999...] (>= 12 would roll into next foot)
     *
     * @param feet   whole or fractional feet (>= 0)
     * @param inches inches component (0.0 <= inches < 12.0)
     * @throws IllegalArgumentException if either parameter is out of range
     */
    public Length(double feet, double inches) {
        // 04-May-2026: Guard — negative feet make no physical sense
        if (feet < 0) {
            throw new IllegalArgumentException(
                "Feet cannot be negative: " + feet
            );
        }
        // 04-May-2026: Guard — inches must be in [0, 12); >= 12 belongs in the feet part
        if (inches < 0 || inches >= INCHES_PER_FOOT) {
            throw new IllegalArgumentException(
                "Inches must be in range [0, 12): " + inches
            );
        }
        this.feet   = feet;
        this.inches = inches;
    }

    /**
     * 30-Apr-2026: Convenience constructor — feet only (inches defaults to 0).
     *              Maintains backward-compatible API for callers who only have feet.
     *
     * @param feet measurement in feet (>= 0)
     */
    public Length(double feet) {
        this(feet, 0.0);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Public accessors
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * 30-Apr-2026: Returns the feet component.
     * Encapsulation: getter exposes the value, not the field itself.
     */
    public double getFeet() {
        return feet;
    }

    /**
     * 30-Apr-2026: Returns the inches component.
     */
    public double getInches() {
        return inches;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Private helper — Normalisation
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * toTotalInches() — UC02 core private helper.
     *
     * 01-May-2026: Converts feet+inches into a single normalised double (total inches).
     *              This is the base unit used internally for equality and hashing.
     *
     * Why total inches, not total feet?
     *   - Integer-free arithmetic; avoids fractional feet (1 ft 6 in = 1.5 ft is lossy
     *     when feet is stored as int, and 18.0 inches is exact).
     *   - Consistent with UC05+ where conversion factors are inch-based.
     *
     * Encapsulation: private — callers never see the normalised value.
     *
     * @return total measurement expressed in inches
     */
    private double toTotalInches() {
        // 01-May-2026: (feet × 12) + inches
        return (feet * INCHES_PER_FOOT) + inches;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // equals, hashCode, toString
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * equals() — measures two Length objects for physical equality.
     *
     * 01-May-2026: Compares normalised total inches (encapsulation hides internals).
     * 02-May-2026: Added null, self-reference, and type guards (carried from UC01).
     *
     * Examples:
     *   new Length(1, 0).equals(new Length(0, 12)) → INVALID (12 in throws)
     *   new Length(2, 0).equals(new Length(1, 12)) → INVALID (12 in throws)
     *   new Length(1, 6).equals(new Length(1, 6))  → true
     *   new Length(1, 0).equals(new Length(1, 1))  → false
     *
     * @param obj object to compare
     * @return true if both represent the same physical length
     */
    @Override
    public boolean equals(Object obj) {
        // 02-May-2026: Guard 1 — null check
        if (obj == null) {
            return false;
        }

        // 02-May-2026: Guard 2 — same reference fast path
        if (this == obj) {
            return true;
        }

        // 02-May-2026: Guard 3 — type safety; Length != Feet != String
        if (!(obj instanceof Length)) {
            return false;
        }

        Length other = (Length) obj;

        // 01-May-2026: Normalise both to total inches then compare with epsilon.
        //              Encapsulation: the normalisation logic stays inside this class.
        return Math.abs(this.toTotalInches() - other.toTotalInches()) < EPSILON;
    }

    /**
     * hashCode() — consistent with equals() contract.
     *
     * 03-May-2026: Hash on normalised total inches (same value used in equals).
     *              Snap to epsilon grid to handle float drift (same strategy as UC01).
     */
    @Override
    public int hashCode() {
        // 03-May-2026: Snap normalised inches to epsilon grid before hashing
        double normalised = toTotalInches();
        long bits = Double.doubleToLongBits(
            Math.round(normalised / EPSILON) * EPSILON
        );
        return Long.hashCode(bits);
    }

    /**
     * 05-May-2026: Human-readable format for logs and test failure messages.
     * Format: "<feet> ft <inches> in"  e.g. "1.0 ft 6.0 in"
     */
    @Override
    public String toString() {
        return feet + " ft " + inches + " in";
    }
}
