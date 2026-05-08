package com.quantitymeasurement;

import com.quantitymeasurement.domain.Quantity;
import com.quantitymeasurement.domain.unit.LengthUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UC5: Unit-to-Unit Conversion — Immutability, Value Object Semantics.
 */
@DisplayName("UC5 - Unit-to-Unit Conversion")
class UC5_UnitConversionTest {

    private static final double DELTA = 0.001;

    @Test
    @DisplayName("1 foot converts to 12 inches")
    void oneFoot_convertsTo_twelveInches() {
        Quantity<LengthUnit> feet   = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = feet.convertTo(LengthUnit.INCH);
        assertEquals(12.0, result.getValue(), DELTA);
        assertEquals(LengthUnit.INCH, result.getUnit());
    }

    @Test
    @DisplayName("36 inches converts to 1 yard")
    void thirtySixInches_convertsTo_oneYard() {
        Quantity<LengthUnit> inches = new Quantity<>(36.0, LengthUnit.INCH);
        Quantity<LengthUnit> result = inches.convertTo(LengthUnit.YARD);
        assertEquals(1.0, result.getValue(), DELTA);
    }

    @Test
    @DisplayName("Immutability: original Quantity unchanged after convert")
    void immutability_originalUnchanged() {
        Quantity<LengthUnit> original = new Quantity<>(1.0, LengthUnit.FEET);
        original.convertTo(LengthUnit.INCH);
        assertEquals(1.0, original.getValue(), DELTA);
        assertEquals(LengthUnit.FEET, original.getUnit());
    }

    @Test
    @DisplayName("Convert to same unit returns same value")
    void convertToSameUnit_returnsIdenticalValue() {
        Quantity<LengthUnit> qty    = new Quantity<>(5.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = qty.convertTo(LengthUnit.FEET);
        assertEquals(5.0, result.getValue(), DELTA);
    }

    @Test
    @DisplayName("5.08 cm converts to 2 inches")
    void fivePointZeroEightCm_convertsTo_twoInches() {
        Quantity<LengthUnit> cm     = new Quantity<>(5.08, LengthUnit.CENTIMETER);
        Quantity<LengthUnit> result = cm.convertTo(LengthUnit.INCH);
        assertEquals(2.0, result.getValue(), DELTA);
    }
}
