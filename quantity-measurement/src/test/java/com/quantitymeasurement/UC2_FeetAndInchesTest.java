package com.quantitymeasurement;

import com.quantitymeasurement.domain.Quantity;
import com.quantitymeasurement.domain.unit.LengthUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UC2: Object Encapsulation — value and unit travel together as one object.
 */
@DisplayName("UC2 - Feet and Inches Measurement Equality")
class UC2_FeetAndInchesTest {

    @Test
    @DisplayName("1 foot == 12 inches")
    void oneFoot_shouldEqual_twelveInches() {
        Quantity<LengthUnit> feet   = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> inches = new Quantity<>(12.0, LengthUnit.INCH);
        assertEquals(feet, inches);
    }

    @Test
    @DisplayName("1 foot != 1 inch")
    void oneFoot_shouldNotEqual_oneInch() {
        Quantity<LengthUnit> feet  = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> inch  = new Quantity<>(1.0, LengthUnit.INCH);
        assertNotEquals(feet, inch);
    }

    @Test
    @DisplayName("2 feet == 24 inches")
    void twoFeet_shouldEqual_twentyFourInches() {
        Quantity<LengthUnit> feet   = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> inches = new Quantity<>(24.0, LengthUnit.INCH);
        assertEquals(feet, inches);
    }

    @Test
    @DisplayName("Encapsulation: value and unit stored together")
    void encapsulation_valueAndUnitAccessible() {
        Quantity<LengthUnit> qty = new Quantity<>(5.0, LengthUnit.FEET);
        assertEquals(5.0, qty.getValue());
        assertEquals(LengthUnit.FEET, qty.getUnit());
    }
}
