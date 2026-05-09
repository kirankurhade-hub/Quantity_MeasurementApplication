package com.qm.uc02;

/**
 * Length.java
 *
 * Author      : Kiran Kurhade
 * Created     : 27-Apr-2026
 * Last Updated: 09-May-2026
 *
 * UC02 - Feet and Inches Measurement Equality
 * Concept: Object Encapsulation
 *
 * Design decision (27-Apr-2026):
 *   UC01 had a single 'feet' field. UC02 extends this to encapsulate
 *   both feet AND inches inside one value object (Length), hiding how
 *   they are stored and normalised from the caller.
 *
 * Changelog:
 *   27-Apr-2026  |  Class created; feet + inches fields defined as final
 *   28-Apr-2026  |  Added two-arg constructor with validation guards
 *   28-Apr-2026  |  Added one-arg convenience constructor (inches defaults to 0)
 *   29-Apr-2026  |  Added private toTotalInches() — encapsulates normalisation
 *   30-Apr-2026  |  Implemented equals() using toTotalInches() + epsilon
 *   01-May-2026  |  Implemented hashCode() consistent with equals()
 *   02-May-2026  |  Added getFeet() / getInches() accessors (read-only)
 *   05-May-2026  |  Added toString() for readable test failure messages
 *   07-May-2026  |  Code review: renamed internal variable 'total' → 'normalised'
 *   09-May-2026  |  Final Javadoc pass; ready for merge to dev
 */
public class Length {

    // 28-Apr-2026: Conversion constant — 1 international foot = exactly 12 inches
    private static final int INCHES_PER_FOOT = 12;

    // 30-Apr-2026: Inherited epsilon policy from UC01 Feet class.
    //              1e-9 inches ≈ 0.025 nanometres — safely absorbs IEEE 754 drift.
    private static final double EPSILON = 1e-9;

    // 27-Apr-2026: Both fields are final — value object, no mutation after construction.
    //              Callers access these only through getFeet() / getInches().
    private final double feet;
    private final double inches;

    /**
     * Primary constructor — encapsulates feet and inches into one value object.
     *
     * Validation added 28-Apr-2026:
     *   - feet   must be >= 0  (negative length is physically meaningless)
     *   - inches must be in [0, 12) to avoid ambiguity with the feet field
     *     e.g. Length(1, 13) would duplicate Length(2, 1) — we prevent this.
     *
     * @param feet   feet component; must be >= 0
     * @param inches inches component; must be in [0, 12)
     * @throws IllegalArgumentException if either argument violates its range
     */
    public Length(double feet, double inches) {
        // 28-Apr-2026: Guard: feet cannot be negative
        if (feet < 0) {
            throw new IllegalArgumentException(
                "Feet cannot be negative. Received: " + feet
            );
        }
        // 28-Apr-2026: Guard: inches must be in [0, 12)
        // If inches >= 12 the caller should have incremented feet instead.
        if (inches < 0 || inches >= INCHES_PER_FOOT) {
            throw new IllegalArgumentException(
                "Inches must be in range [0, 12). Received: " + inches
            );
        }
        this.feet   = feet;
        this.inches = inches;
    }

    /**
     * Convenience constructor for feet-only measurements.
     * Added 28-Apr-2026 so callers don't need to write new Length(3.0, 0.0).
     *
     * @param feet feet component; must be >= 0
     */
    public Length(double feet) {
        // Delegate to primary constructor — validation runs once
        this(feet, 0.0);
    }

    // ─── Accessors ────────────────────────────────────────────────────────────

    /**
     * Returns the feet component of this measurement.
     * Added 02-May-2026 — read-only; no setter to preserve immutability.
     */
    public double getFeet() {
        return feet;
    }

    /**
     * Returns the inches component of this measurement.
     * Added 02-May-2026 — read-only; no setter to preserve immutability.
     */
    public double getInches() {
        return inches;
    }

    // ─── Private normalisation ────────────────────────────────────────────────

    /**
     * Converts this Length to a single value in inches.
     *
     * 29-Apr-2026: This is the KEY encapsulation decision.
     *   - The caller never knows we store feet and inches separately.
     *   - equals() and hashCode() both call this — single source of truth.
     *   - Making it private ensures only this class controls the conversion.
     *
     * Formula: totalInches = (feet × 12) + inches
     */
    private double toTotalInches() {
        return (feet * INCHES_PER_FOOT) + inches;
    }

    // ─── equals() ─────────────────────────────────────────────────────────────

    /**
     * Physical equality — two Length objects are equal when they represent
     * the same total measurement in inches (within epsilon tolerance).
     *
     * 30-Apr-2026: Delegates comparison to toTotalInches() so callers
     *   never need to know about feet-to-inches conversion.
     *
     * Guard sequence (mirrors UC01):
     *   1. null check   — avoid NullPointerException
     *   2. self check   — reflexivity fast-path
     *   3. type check   — symmetry guarantee
     *   4. epsilon cmp  — floating-point safe comparison
     *
     * @param obj the object to compare
     * @return true if both lengths are physically equal within EPSILON
     */
    @Override
    public boolean equals(Object obj) {
        // Guard 1 — null (30-Apr-2026)
        if (obj == null) return false;
        // Guard 2 — same reference
        if (this == obj) return true;
        // Guard 3 — type safety
        if (!(obj instanceof Length)) return false;

        Length other = (Length) obj;
        // Guard 4 — normalise both to total inches, then epsilon-compare
        return Math.abs(this.toTotalInches() - other.toTotalInches()) < EPSILON;
    }

    // ─── hashCode() ───────────────────────────────────────────────────────────

    /**
     * Hash code consistent with equals().
     * Java contract: a.equals(b) → a.hashCode() == b.hashCode()
     *
     * 01-May-2026: Snap normalised value to the epsilon grid so two
     *   physically-equal lengths always land in the same hash bucket.
     */
    @Override
    public int hashCode() {
        // 01-May-2026: Use same normalisation as equals()
        double normalised = toTotalInches();
        long bits = Double.doubleToLongBits(
            Math.round(normalised / EPSILON) * EPSILON
        );
        return Long.hashCode(bits);
    }

    // ─── toString() ───────────────────────────────────────────────────────────

    /**
     * 05-May-2026: Readable string for logs and test failure messages.
     * Format: "2.0 ft 6.0 in"
     */
    @Override
    public String toString() {
        return feet + " ft " + inches + " in";
    }
}
