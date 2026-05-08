package com.quantitymeasurement;

import com.quantitymeasurement.domain.Quantity;
import com.quantitymeasurement.domain.unit.LengthUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UC6: Addition of Two Length Units.
 * Tests: Arithmetic on Value Objects, Immutability, Normalization to Base Unit,
 * Commutativity, Unit Conversion Reusability.
 */
@DisplayName("UC6 - Addition of Two Length Units")
class UC6_AdditionTest {

    private static final double DELTA = 0.001;

    @Test
    @DisplayName("2 feet + 2 feet = 4 feet")
    void addSameUnit_feetPlusFeet() {
        Quantity<LengthUnit> a      = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> b      = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = a.add(b);
        assertEquals(4.0, result.getValue(), DELTA);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    @DisplayName("1 foot + 12 inches = 2 feet")
    void addMixedUnits_footPlusInches() {
        Quantity<LengthUnit> foot   = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> inches = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> result = foot.add(inches);
        assertEquals(2.0, result.getValue(), DELTA);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    @DisplayName("Commutativity: a + b == b + a (same base magnitude)")
    void addition_isCommutativeInBaseUnit() {
        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCH);
        assertEquals(a.add(b), b.add(a));
    }

    @Test
    @DisplayName("Immutability: operands unchanged after addition")
    void addition_doesNotMutateOperands() {
        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(6.0, LengthUnit.INCH);
        a.add(b);
        assertEquals(1.0, a.getValue(), DELTA);
        assertEquals(6.0, b.getValue(), DELTA);
    }

    @Test
    @DisplayName("2 inches + 2.5 cm ≈ 2.984 inches")
    void addInchesAndCentimetres() {
        Quantity<LengthUnit> inches = new Quantity<>(2.0, LengthUnit.INCH);
        Quantity<LengthUnit> cm     = new Quantity<>(2.5, LengthUnit.CENTIMETER);
        Quantity<LengthUnit> result = inches.add(cm);
        // 2.5 cm = 0.984252 inches → total ≈ 2.984252 inches
        assertEquals(2.984, result.getValue(), DELTA);
    }
}
