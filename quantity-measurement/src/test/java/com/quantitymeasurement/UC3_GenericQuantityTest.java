package com.quantitymeasurement;

import com.quantitymeasurement.domain.Quantity;
import com.quantitymeasurement.domain.unit.LengthUnit;
import com.quantitymeasurement.domain.unit.WeightUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UC3: DRY Principle — one generic Quantity class works across all measurement categories.
 * Demonstrates Polymorphism, ENUM Usage, Abstraction, equals Override.
 */
@DisplayName("UC3 - Generic Quantity Class (DRY Principle)")
class UC3_GenericQuantityTest {

    @Test
    @DisplayName("Generic Quantity works for LENGTH units")
    void genericQuantity_worksForLength() {
        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCH);
        assertEquals(a, b);
    }

    @Test
    @DisplayName("Generic Quantity works for WEIGHT units")
    void genericQuantity_worksForWeight() {
        Quantity<WeightUnit> a = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> b = new Quantity<>(1000.0, WeightUnit.GRAM);
        assertEquals(a, b);
    }

    @Test
    @DisplayName("Same class handles different categories (DRY)")
    void sameClass_differentCategories() {
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        assertEquals("com.quantitymeasurement.domain.Quantity",
                     length.getClass().getName());
        assertEquals("com.quantitymeasurement.domain.Quantity",
                     weight.getClass().getName());
    }

    @Test
    @DisplayName("Cross-category quantities never equal each other")
    void crossCategory_shouldNeverBeEqual() {
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        // equals must check category to prevent this
        assertNotEquals((Object) length, (Object) weight);
    }
}
