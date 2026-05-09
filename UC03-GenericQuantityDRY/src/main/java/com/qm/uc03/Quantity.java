package com.qm.uc03;

/**
 * Generic, immutable value object for any measurable quantity.
 *
 * <h3>DRY rationale</h3>
 * <p>UC01 had a {@code Feet} class and UC02 had a {@code Length} class.
 * Both repeated the same pattern: a numeric value, a unit, epsilon-based
 * equality, and a normalised hash. This class eliminates that duplication
 * by parameterising over the unit type.</p>
 *
 * <h3>Design</h3>
 * <ul>
 *   <li>{@code T extends Enum<T>} constrains the unit to an enum, enabling
 *       compile-time type safety while still being polymorphic.</li>
 *   <li>The {@code baseConversionFactor} field is read directly from the
 *       enum constant, so each unit carries its own conversion knowledge
 *       (Single Responsibility for the enum).</li>
 *   <li>Normalisation to the base unit happens inside {@link #toBaseUnit()},
 *       a private method — callers never see it.</li>
 * </ul>
 *
 * @param <T> the enum type representing the measurement unit
 *
 * @author BridgeLabz Dev Team
 * @since 02-May-2026
 */
public class Quantity<T extends Enum<T>> {

    /** Floating-point tolerance used across all equality comparisons. */
    private static final double EPSILON = 1e-9;

    private final double value;
    private final T unit;

    /**
     * Creates a new {@code Quantity}.
     *
     * @param value the numeric measurement; must be &ge; 0
     * @param unit  the unit of measurement; must not be {@code null}
     * @throws IllegalArgumentException if {@code value} is negative
     * @throws NullPointerException     if {@code unit} is {@code null}
     */
    public Quantity(double value, T unit) {
        if (value < 0) {
            throw new IllegalArgumentException(
                "Measurement value cannot be negative: " + value
            );
        }
        if (unit == null) {
            throw new NullPointerException("Unit must not be null");
        }
        this.value = value;
        this.unit  = unit;
    }

    /** Returns the numeric value of this measurement. */
    public double getValue() { return value; }

    /** Returns the unit of this measurement. */
    public T getUnit() { return unit; }

    /**
     * Converts this measurement to a value in the base unit of its category.
     *
     * <p>The conversion factor is obtained from the enum constant itself,
     * keeping conversion knowledge inside the enum (SRP).</p>
     *
     * <p>This uses reflection-free enum access: the {@code baseConversionFactor}
     * field is package-private on {@link LengthUnit}. For a fully generic
     * solution across multiple unit enums see UC10 where an interface is
     * introduced.</p>
     */
    private double toBaseUnit() {
        if (unit instanceof LengthUnit) {
            return value * ((LengthUnit) unit).baseConversionFactor;
        }
        // Fallback — unit carries no factor, treat value as already in base unit.
        return value;
    }

    /**
     * Compares this quantity to {@code obj} for physical equality.
     *
     * <p>Two quantities are equal when they belong to the same unit category
     * and their normalised base-unit values are within {@link #EPSILON}.</p>
     *
     * @param obj the object to compare
     * @return {@code true} if both represent the same physical measurement
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (!(obj instanceof Quantity)) return false;

        Quantity<?> other = (Quantity<?>) obj;

        // Units must belong to the same enum type; comparing metres to kilograms
        // must always return false.
        if (!this.unit.getClass().equals(other.unit.getClass())) return false;

        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
    }

    /**
     * Hash consistent with {@link #equals}: derived from the normalised
     * base-unit value snapped to the epsilon grid.
     */
    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(
            Math.round(toBaseUnit() / EPSILON) * EPSILON
        );
        return 31 * unit.getClass().hashCode() + Long.hashCode(bits);
    }

    @Override
    public String toString() {
        return value + " " + unit.name();
    }
}
