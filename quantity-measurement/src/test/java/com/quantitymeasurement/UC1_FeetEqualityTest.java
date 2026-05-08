package com.quantitymeasurement;

import com.quantitymeasurement.domain.Quantity;
import com.quantitymeasurement.domain.unit.LengthUnit;
import com.quantitymeasurement.exception.QuantityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UC1: Object Equality, Floating-point Comparison, Null Checking, Type Checking
 */
@DisplayName("UC1 - Feet Measurement Equality")
class UC1_FeetEqualityTest {

    @Test
    @DisplayName("Same value same unit → equal")
    void sameValueSameUnit_shouldBeEqual() {
        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(1.0, LengthUnit.FEET);
        assertEquals(a, b);
    }

    @Test
    @DisplayName("Different values → not equal")
    void differentValues_shouldNotBeEqual() {
        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);
        assertNotEquals(a, b);
    }

    @Test
    @DisplayName("Floating-point identical values → equal")
    void floatingPointSameValue_shouldBeEqual() {
        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(1.0, LengthUnit.FEET);
        assertEquals(a, b);
    }

    @Test
    @DisplayName("Null unit → QuantityException")
    void nullUnit_shouldThrowException() {
        assertThrows(QuantityException.class, () -> new Quantity<>(1.0, null));
    }

    @Test
    @DisplayName("Quantity != null object")
    void compareWithNull_shouldNotBeEqual() {
        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
        assertNotEquals(null, a);
    }

    @Test
    @DisplayName("Quantity != String type")
    void compareWithString_shouldNotBeEqual() {
        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
        assertNotEquals("1.0 FEET", a);
    }

    @Test
    @DisplayName("Same object reference → equal (reflexive)")
    void sameReference_shouldBeEqual() {
        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
        assertEquals(a, a);
    }
}
