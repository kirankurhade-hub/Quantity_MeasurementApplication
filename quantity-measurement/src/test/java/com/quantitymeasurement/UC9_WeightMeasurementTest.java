package com.quantitymeasurement;

import com.quantitymeasurement.domain.Quantity;
import com.quantitymeasurement.domain.unit.WeightUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UC9: Weight Measurement — proves generic design scales to a second category.
 * Tests: Equality, Conversion, Addition across GRAM / KILOGRAM / TONNE / POUND.
 */
@DisplayName("UC9 - Weight Measurement")
class UC9_WeightMeasurementTest {

    private static final double DELTA = 0.001;

    @Test
    @DisplayName("1 kg == 1000 g")
    void oneKilogram_shouldEqual_thousandGrams() {
        Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> g  = new Quantity<>(1000.0, WeightUnit.GRAM);
        assertEquals(kg, g);
    }

    @Test
    @DisplayName("1 tonne == 1000 kg")
    void oneTonne_shouldEqual_thousandKilograms() {
        Quantity<WeightUnit> tonne = new Quantity<>(1.0, WeightUnit.TONNE);
        Quantity<WeightUnit> kg    = new Quantity<>(1000.0, WeightUnit.KILOGRAM);
        assertEquals(tonne, kg);
    }

    @Test
    @DisplayName("1 pound ≈ 453.592 grams (conversion factor accuracy)")
    void onePound_conversionToGrams() {
        Quantity<WeightUnit> pound  = new Quantity<>(1.0, WeightUnit.POUND);
        Quantity<WeightUnit> result = pound.convertTo(WeightUnit.GRAM);
        assertEquals(453.592, result.getValue(), DELTA);
    }

    @Test
    @DisplayName("500 g + 500 g = 1 kg (target = KILOGRAM)")
    void addGrams_resultInKilograms() {
        Quantity<WeightUnit> a = new Quantity<>(500.0, WeightUnit.GRAM);
        Quantity<WeightUnit> b = new Quantity<>(500.0, WeightUnit.GRAM);
        Quantity<WeightUnit> result = a.add(b, WeightUnit.KILOGRAM);
        assertEquals(1.0, result.getValue(), DELTA);
    }

    @Test
    @DisplayName("1 kg + 1 pound ≈ 1.454 kg")
    void addKilogramAndPound() {
        Quantity<WeightUnit> kg    = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> pound = new Quantity<>(1.0, WeightUnit.POUND);
        Quantity<WeightUnit> result = kg.add(pound);
        // 1 kg = 1000 g, 1 lb = 453.592 g → total 1453.592 g = 1.453592 kg
        assertEquals(1.454, result.getValue(), DELTA);
    }

    @Test
    @DisplayName("Immutability: weight operands unchanged after add")
    void weight_immutabilityAfterAdd() {
        Quantity<WeightUnit> a = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> b = new Quantity<>(500.0, WeightUnit.GRAM);
        a.add(b);
        assertEquals(1.0, a.getValue(), DELTA);
    }
}
