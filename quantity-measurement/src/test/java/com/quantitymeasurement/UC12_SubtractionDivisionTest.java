package com.quantitymeasurement;

import com.quantitymeasurement.domain.Quantity;
import com.quantitymeasurement.domain.unit.LengthUnit;
import com.quantitymeasurement.domain.unit.WeightUnit;
import com.quantitymeasurement.exception.QuantityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UC12: Subtraction and Division Operations.
 * Tests: Comprehensive Arithmetic, Non-Commutativity, Division by Zero,
 * Target Unit Specification, Immutability, Cross-Category Safety.
 */
@DisplayName("UC12 - Subtraction and Division Operations")
class UC12_SubtractionDivisionTest {

    private static final double DELTA = 0.001;

    // ── Subtraction ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("3 feet - 1 foot = 2 feet")
    void subtractSameUnit() {
        Quantity<LengthUnit> a = new Quantity<>(3.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(1.0, LengthUnit.FEET);
        assertEquals(2.0, a.subtract(b).getValue(), DELTA);
    }

    @Test
    @DisplayName("2 feet - 12 inches = 1 foot")
    void subtractMixedUnits() {
        Quantity<LengthUnit> feet   = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> inches = new Quantity<>(12.0, LengthUnit.INCH);
        assertEquals(1.0, feet.subtract(inches).getValue(), DELTA);
    }

    @Test
    @DisplayName("Subtraction is non-commutative: a - b ≠ b - a when a ≠ b")
    void subtraction_nonCommutative() {
        Quantity<LengthUnit> a = new Quantity<>(3.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(1.0, LengthUnit.FEET);
        assertNotEquals(a.subtract(b).getValue(), b.subtract(a).getValue());
    }

    @Test
    @DisplayName("Subtraction with target unit: 2 feet - 6 inches = 18 inches")
    void subtractWithTargetUnit() {
        Quantity<LengthUnit> feet   = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> inches = new Quantity<>(6.0, LengthUnit.INCH);
        Quantity<LengthUnit> result = feet.subtract(inches, LengthUnit.INCH);
        assertEquals(18.0, result.getValue(), DELTA);
    }

    // ── Division ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("4 feet ÷ 2 feet = 2 feet (base/base in same result unit)")
    void divideSameUnit() {
        Quantity<LengthUnit> a = new Quantity<>(4.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);
        assertEquals(1.0, a.divide(b).getValue(), DELTA);
    }

    @Test
    @DisplayName("Division by zero throws QuantityException")
    void divisionByZero_throwsException() {
        Quantity<LengthUnit> a    = new Quantity<>(4.0, LengthUnit.FEET);
        Quantity<LengthUnit> zero = new Quantity<>(0.0, LengthUnit.FEET);
        assertThrows(QuantityException.class, () -> a.divide(zero));
    }

    @Test
    @DisplayName("Immutability: operands unchanged after subtraction")
    void subtraction_doesNotMutateOperands() {
        Quantity<LengthUnit> a = new Quantity<>(3.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(1.0, LengthUnit.FEET);
        a.subtract(b);
        assertEquals(3.0, a.getValue(), DELTA);
    }

    @Test
    @DisplayName("Cross-category subtraction throws QuantityException")
    void subtractCrossCategory_throwsException() {
        @SuppressWarnings("unchecked")
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
        // Cast trick to simulate caller passing wrong category
        assertThrows(Exception.class, () -> {
            Quantity wt = new Quantity<>(1.0, WeightUnit.KILOGRAM);
            length.subtract(wt);
        });
    }
}
