package com.quantitymeasurement;

import com.quantitymeasurement.domain.Quantity;
import com.quantitymeasurement.domain.unit.LengthUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UC7: Addition with Target Unit Specification.
 * Tests: Method Overloading, Explicit Parameter Passing,
 * Flexibility in Result Representation, Unit Independence in Arithmetic.
 */
@DisplayName("UC7 - Addition with Target Unit Specification")
class UC7_TargetUnitAdditionTest {

    private static final double DELTA = 0.001;

    @Test
    @DisplayName("1 foot + 12 inches = 24 inches (target = INCH)")
    void addWithTargetUnit_inch() {
        Quantity<LengthUnit> foot   = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> inches = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> result = foot.add(inches, LengthUnit.INCH);
        assertEquals(24.0, result.getValue(), DELTA);
        assertEquals(LengthUnit.INCH, result.getUnit());
    }

    @Test
    @DisplayName("1 foot + 1 yard = 4 feet (target = FEET)")
    void addWithTargetUnit_feet() {
        Quantity<LengthUnit> foot = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> result = foot.add(yard, LengthUnit.FEET);
        assertEquals(4.0, result.getValue(), DELTA);
    }

    @Test
    @DisplayName("1 foot + 1 yard = 48 inches (target = INCH)")
    void addWithTargetUnit_inches_crossUnit() {
        Quantity<LengthUnit> foot = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> result = foot.add(yard, LengthUnit.INCH);
        assertEquals(48.0, result.getValue(), DELTA);
    }

    @Test
    @DisplayName("add() without target defaults to caller's unit")
    void addWithoutTarget_defaultsToCallerUnit() {
        Quantity<LengthUnit> foot   = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> inches = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> result = foot.add(inches);       // overloaded, no target
        assertEquals(LengthUnit.FEET, result.getUnit());
        assertEquals(2.0, result.getValue(), DELTA);
    }
}
