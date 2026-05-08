package com.quantitymeasurement;

import com.quantitymeasurement.domain.Quantity;
import com.quantitymeasurement.domain.unit.LengthUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UC4: Extended Unit Support — YARD and CENTIMETER added to LengthUnit.
 * Validates Scalability, ENUM Extensibility, Mathematical Accuracy, Backward Compatibility.
 */
@DisplayName("UC4 - Extended Unit Support")
class UC4_ExtendedUnitSupportTest {

    private static final double DELTA = 0.001;

    @Test
    @DisplayName("1 yard == 3 feet")
    void oneYard_shouldEqual_threeFeet() {
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> feet = new Quantity<>(3.0, LengthUnit.FEET);
        assertEquals(yard, feet);
    }

    @Test
    @DisplayName("1 yard == 36 inches")
    void oneYard_shouldEqual_thirtySixInches() {
        Quantity<LengthUnit> yard   = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> inches = new Quantity<>(36.0, LengthUnit.INCH);
        assertEquals(yard, inches);
    }

    @Test
    @DisplayName("2 inches == 5.08 centimetres")
    void twoInches_shouldEqual_fivePointZeroEightCm() {
        Quantity<LengthUnit> inches = new Quantity<>(2.0, LengthUnit.INCH);
        Quantity<LengthUnit> cm     = new Quantity<>(5.08, LengthUnit.CENTIMETER);
        assertEquals(inches, cm);
    }

    @Test
    @DisplayName("1 centimetre base conversion accuracy")
    void centimetre_conversionFactor() {
        // 1 cm = 0.393701 inches
        double base = LengthUnit.CENTIMETER.toBase(1.0);
        assertEquals(0.393701, base, DELTA);
    }

    @Test
    @DisplayName("Backward compatibility: FEET and INCH equality still works")
    void backwardCompatibility_feetAndInches() {
        Quantity<LengthUnit> feet   = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> inches = new Quantity<>(12.0, LengthUnit.INCH);
        assertEquals(feet, inches);
    }
}
