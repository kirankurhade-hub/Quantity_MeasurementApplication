package com.qm.uc03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Quantity} — UC03: Generic Quantity Class (DRY Principle).
 *
 * <p>Verifies that the single generic class correctly replaces the separate
 * {@code Feet} and {@code Length} classes from UC01/UC02, while providing
 * correct equality, normalisation, and type safety across unit conversions.</p>
 *
 * @author BridgeLabz Dev Team
 * @since 04-May-2026
 */
@DisplayName("UC03 - Generic Quantity Class for DRY Principle")
class QuantityTest {

    // ── Same-unit equality ────────────────────────────────────────────────────

    @Test
    @DisplayName("TC01 - Same value and same unit should be equal")
    void testSameValueSameUnit() {
        assertEquals(
            new Quantity<>(5.0, LengthUnit.FOOT),
            new Quantity<>(5.0, LengthUnit.FOOT)
        );
    }

    @Test
    @DisplayName("TC02 - Different values with same unit should not be equal")
    void testDifferentValueSameUnit() {
        assertNotEquals(
            new Quantity<>(3.0, LengthUnit.FOOT),
            new Quantity<>(5.0, LengthUnit.FOOT)
        );
    }

    // ── Cross-unit equality (normalised to base) ──────────────────────────────

    @Test
    @DisplayName("TC03 - 1 FOOT should equal 12 INCHes")
    void testOneFootEqualsTwelveInches() {
        assertEquals(
            new Quantity<>(1.0, LengthUnit.FOOT),
            new Quantity<>(12.0, LengthUnit.INCH)
        );
    }

    @Test
    @DisplayName("TC04 - 1 YARD should equal 36 INCHes")
    void testOneYardEqualsThirtySixInches() {
        assertEquals(
            new Quantity<>(1.0, LengthUnit.YARD),
            new Quantity<>(36.0, LengthUnit.INCH)
        );
    }

    @Test
    @DisplayName("TC05 - 1 YARD should equal 3 FOOTs")
    void testOneYardEqualsThreeFeet() {
        assertEquals(
            new Quantity<>(1.0, LengthUnit.YARD),
            new Quantity<>(3.0, LengthUnit.FOOT)
        );
    }

    @Test
    @DisplayName("TC06 - 2 FOOTs should not equal 25 INCHes")
    void testTwoFeetNotEqualTwentyFiveInches() {
        assertNotEquals(
            new Quantity<>(2.0, LengthUnit.FOOT),
            new Quantity<>(25.0, LengthUnit.INCH)
        );
    }

    // ── Null & type safety ────────────────────────────────────────────────────

    @Test
    @DisplayName("TC07 - Comparison with null should return false")
    void testCompareWithNull() {
        assertNotEquals(new Quantity<>(5.0, LengthUnit.FOOT), null);
    }

    @Test
    @DisplayName("TC08 - Comparison with a String should return false")
    void testCompareWithString() {
        assertNotEquals(new Quantity<>(5.0, LengthUnit.FOOT), "5.0 FOOT");
    }

    @Test
    @DisplayName("TC09 - Null unit should throw NullPointerException")
    void testNullUnitThrows() {
        assertThrows(NullPointerException.class,
            () -> new Quantity<>(5.0, null)
        );
    }

    // ── Validation ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC10 - Negative value should throw IllegalArgumentException")
    void testNegativeValueThrows() {
        assertThrows(IllegalArgumentException.class,
            () -> new Quantity<>(-1.0, LengthUnit.FOOT)
        );
    }

    // ── equals() contract ─────────────────────────────────────────────────────

    @Test
    @DisplayName("TC11 - Reflexivity: a.equals(a)")
    void testReflexivity() {
        Quantity<LengthUnit> a = new Quantity<>(4.0, LengthUnit.FOOT);
        assertEquals(a, a);
    }

    @Test
    @DisplayName("TC12 - Symmetry: a.equals(b) ↔ b.equals(a)")
    void testSymmetry() {
        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FOOT);
        Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCH);
        assertEquals(a, b);
        assertEquals(b, a);
    }

    @Test
    @DisplayName("TC13 - Transitivity: a==b, b==c → a==c")
    void testTransitivity() {
        Quantity<LengthUnit> a = new Quantity<>(36.0, LengthUnit.INCH);
        Quantity<LengthUnit> b = new Quantity<>(3.0,  LengthUnit.FOOT);
        Quantity<LengthUnit> c = new Quantity<>(1.0,  LengthUnit.YARD);
        assertEquals(a, b);
        assertEquals(b, c);
        assertEquals(a, c);
    }

    // ── Polymorphism & DRY ────────────────────────────────────────────────────

    @Test
    @DisplayName("TC14 - Single Quantity class handles INCH, FOOT, and YARD without duplication")
    void testPolymorphicUsage() {
        // All three use the same Quantity class — no separate InchMeasurement,
        // FootMeasurement, or YardMeasurement classes needed.
        Quantity<LengthUnit> inch = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> foot = new Quantity<>(1.0,  LengthUnit.FOOT);
        Quantity<LengthUnit> yard = new Quantity<>(0.333333333, LengthUnit.YARD);

        assertEquals(inch, foot);
        assertNotEquals(foot, yard);
    }

    // ── hashCode ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC15 - Equal Quantity objects must have equal hashCodes")
    void testHashCodeConsistency() {
        assertEquals(
            new Quantity<>(1.0, LengthUnit.FOOT).hashCode(),
            new Quantity<>(12.0, LengthUnit.INCH).hashCode()
        );
    }

    // ── Zero edge case ────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC16 - Zero INCH should equal zero FOOT")
    void testZeroEquality() {
        assertEquals(
            new Quantity<>(0.0, LengthUnit.INCH),
            new Quantity<>(0.0, LengthUnit.FOOT)
        );
    }
}
