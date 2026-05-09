package com.qm.uc03;

/**
 * Enumeration of supported length units.
 *
 * <p>Each constant carries a {@code baseConversionFactor} that expresses
 * how many inches equal one unit of this type. The inch is chosen as the
 * base unit because it is the smallest standard unit in this set, keeping
 * all factors whole or simple decimal numbers.</p>
 *
 * <p>Adding a new unit only requires appending a constant here — no other
 * class needs to change (Open/Closed Principle).</p>
 *
 * @author BridgeLabz Dev Team
 * @since 02-May-2026
 */
public enum LengthUnit {

    /**
     * Inch — the base unit for length conversion in this system.
     * Factor: 1 inch = 1 inch.
     */
    INCH(1.0),

    /**
     * Foot — 1 foot = 12 inches.
     */
    FOOT(12.0),

    /**
     * Yard — 1 yard = 36 inches.
     */
    YARD(36.0);

    /**
     * Number of inches equivalent to one unit of this type.
     * Kept package-private so {@link Quantity} can read it directly.
     */
    final double baseConversionFactor;

    LengthUnit(double baseConversionFactor) {
        this.baseConversionFactor = baseConversionFactor;
    }
}
