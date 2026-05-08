package com.quantitymeasurement.domain;

import com.quantitymeasurement.domain.unit.IMeasurable;
import com.quantitymeasurement.exception.QuantityException;

import java.util.Objects;

/**
 * UC3:  Generic, immutable value object representing a quantity in any unit category.
 * UC5:  Conversion between units of the same category.
 * UC6:  Addition of two quantities (result expressed in this unit).
 * UC7:  Addition with explicit target unit.
 * UC10: Bounded by IMeasurable so it works with Length, Weight, Volume, Temperature.
 * UC12: Subtraction and division operations.
 * UC13: All arithmetic routes through {@link #performOperation} (DRY / centralised).
 *
 * <p>Immutability guarantee: every operation returns a new Quantity; no setters exist.
 */
public class Quantity<U extends IMeasurable> {

    private static final double EPSILON = 0.001;

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null) throw new QuantityException("Unit cannot be null");
        this.value = value;
        this.unit = unit;
    }

    // ── Accessors ─────────────────────────────────────────────────────────────

    public double getValue() { return value; }
    public U getUnit()       { return unit; }

    // ── UC5: Conversion ───────────────────────────────────────────────────────

    /**
     * Returns a new Quantity with the same physical magnitude expressed in targetUnit.
     */
    public Quantity<U> convertTo(U targetUnit) {
        validateSameCategory(targetUnit);
        double baseValue = unit.toBase(value);
        return new Quantity<>(targetUnit.fromBase(baseValue), targetUnit);
    }

    // ── UC6 / UC7: Addition ───────────────────────────────────────────────────

    public Quantity<U> add(Quantity<U> other) {
        return performOperation(other, Operation.ADD, unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        return performOperation(other, Operation.ADD, targetUnit);
    }

    // ── UC12: Subtraction ─────────────────────────────────────────────────────

    public Quantity<U> subtract(Quantity<U> other) {
        return performOperation(other, Operation.SUBTRACT, unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        return performOperation(other, Operation.SUBTRACT, targetUnit);
    }

    // ── UC12: Division ────────────────────────────────────────────────────────

    public Quantity<U> divide(Quantity<U> other) {
        return performOperation(other, Operation.DIVIDE, unit);
    }

    public Quantity<U> divide(Quantity<U> other, U targetUnit) {
        return performOperation(other, Operation.DIVIDE, targetUnit);
    }

    // ── UC13: Centralised arithmetic ──────────────────────────────────────────

    /**
     * Single source of truth for all arithmetic on quantities.
     * Uses {@link Operation} enum with lambda expressions (functional interface).
     */
    private Quantity<U> performOperation(Quantity<U> other, Operation op, U targetUnit) {
        validateOperationSupported(op);
        validateSameCategory(other.unit);
        validateSameCategory(targetUnit);

        double thisBase  = unit.toBase(value);
        double otherBase = other.unit.toBase(other.value);
        double resultBase = op.apply(thisBase, otherBase);

        return new Quantity<>(targetUnit.fromBase(resultBase), targetUnit);
    }

    // ── Validation helpers ────────────────────────────────────────────────────

    private void validateOperationSupported(Operation op) {
        boolean supported = switch (op) {
            case ADD      -> unit.supportsAddition();
            case SUBTRACT -> unit.supportsSubtraction();
            case DIVIDE   -> unit.supportsDivision();
            case MULTIPLY -> unit.supportsMultiplication();
        };
        if (!supported) {
            throw new QuantityException(
                op.name() + " is not supported for category: " + unit.getCategory());
        }
    }

    private void validateSameCategory(IMeasurable other) {
        if (!unit.getCategory().equals(other.getCategory())) {
            throw new QuantityException(
                "Category mismatch: cannot mix " + unit.getCategory()
                + " with " + other.getCategory());
        }
    }

    // ── UC1: Object Equality ──────────────────────────────────────────────────

    /**
     * Two quantities are equal when they represent the same physical magnitude
     * (compared in the base unit within floating-point tolerance).
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity<?> other)) return false;
        if (!unit.getCategory().equals(other.unit.getCategory())) return false;
        return Math.abs(unit.toBase(value) - other.unit.toBase(other.value)) < EPSILON;
    }

    @Override
    public int hashCode() {
        long rounded = Math.round(unit.toBase(value) / EPSILON);
        return Objects.hash(unit.getCategory(), rounded);
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}
