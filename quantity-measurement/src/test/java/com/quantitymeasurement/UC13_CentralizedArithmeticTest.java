package com.quantitymeasurement;

import com.quantitymeasurement.domain.Operation;
import com.quantitymeasurement.domain.Quantity;
import com.quantitymeasurement.domain.unit.LengthUnit;
import com.quantitymeasurement.domain.unit.VolumeUnit;
import com.quantitymeasurement.domain.unit.WeightUnit;
import com.quantitymeasurement.exception.QuantityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UC13: Centralised Arithmetic Logic.
 * Tests: DRY Principle, Lambda/Functional Interface dispatch via Operation enum,
 * Single Source of Truth, consistent error handling across all operations.
 */
@DisplayName("UC13 - Centralised Arithmetic Logic (DRY)")
class UC13_CentralizedArithmeticTest {

    private static final double DELTA = 0.001;

    @Test
    @DisplayName("Operation.ADD applies lambda correctly")
    void operationEnum_add() {
        assertEquals(5.0, Operation.ADD.apply(3.0, 2.0), DELTA);
    }

    @Test
    @DisplayName("Operation.SUBTRACT applies lambda correctly")
    void operationEnum_subtract() {
        assertEquals(1.0, Operation.SUBTRACT.apply(3.0, 2.0), DELTA);
    }

    @Test
    @DisplayName("Operation.MULTIPLY applies lambda correctly")
    void operationEnum_multiply() {
        assertEquals(6.0, Operation.MULTIPLY.apply(3.0, 2.0), DELTA);
    }

    @Test
    @DisplayName("Operation.DIVIDE applies lambda correctly")
    void operationEnum_divide() {
        assertEquals(1.5, Operation.DIVIDE.apply(3.0, 2.0), DELTA);
    }

    @Test
    @DisplayName("Operation.DIVIDE with zero throws QuantityException (lambda guard)")
    void operationEnum_divideByZero() {
        assertThrows(QuantityException.class, () -> Operation.DIVIDE.apply(3.0, 0.0));
    }

    @Test
    @DisplayName("All operations route through single performOperation — verified by consistent results across categories")
    void centralizedRouting_lengthWeightVolume_allWork() {
        // LENGTH
        assertEquals(4.0, new Quantity<>(2.0, LengthUnit.FEET)
            .add(new Quantity<>(2.0, LengthUnit.FEET)).getValue(), DELTA);

        // WEIGHT
        assertEquals(2000.0, new Quantity<>(1.0, WeightUnit.KILOGRAM)
            .add(new Quantity<>(1000.0, WeightUnit.GRAM)).getValue(), DELTA);

        // VOLUME
        assertEquals(2.0, new Quantity<>(1.0, VolumeUnit.LITRE)
            .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)).getValue(), DELTA);
    }

    @Test
    @DisplayName("Unsupported operation throws consistent QuantityException")
    void unsupportedOperation_throwsQuantityException() {
        // Temperature does not support add
        Quantity<com.quantitymeasurement.domain.unit.TemperatureUnit> c =
            new Quantity<>(100.0, com.quantitymeasurement.domain.unit.TemperatureUnit.CELSIUS);
        assertThrows(QuantityException.class, () -> c.add(c));
    }
}
