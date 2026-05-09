package com.qm.uc01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Feet} — UC01: Feet Measurement Equality.
 *
 * <p>Covers: object equality contract, floating-point tolerance,
 * null and type safety, edge cases, and {@code hashCode} consistency.</p>
 *
 * @author BridgeLabz Dev Team
 * @since 25-Apr-2026
 */
@DisplayName("UC01 - Feet Measurement Equality")
class FeetTest {

    // ── Basic equality ────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC01 - Same value should be equal")
    void testEqualFeetValues() {
        assertEquals(new Feet(5.0), new Feet(5.0));
    }

    @Test
    @DisplayName("TC02 - Different values should not be equal")
    void testInequalFeetValues() {
        assertNotEquals(new Feet(3.0), new Feet(5.0));
    }

    // ── Null & type safety ────────────────────────────────────────────────────

    @Test
    @DisplayName("TC03 - Comparison with null should return false")
    void testCompareWithNull() {
        assertNotEquals(null, new Feet(5.0));
    }

    @Test
    @DisplayName("TC04 - Comparison with a String should return false")
    void testCompareWithString() {
        assertNotEquals(new Feet(5.0), "5.0");
    }

    @Test
    @DisplayName("TC05 - Comparison with a raw Double should return false")
    void testCompareWithDouble() {
        assertNotEquals(new Feet(5.0), 5.0);
    }

    // ── Floating-point precision ──────────────────────────────────────────────

    @Test
    @DisplayName("TC06 - Values within EPSILON tolerance should be equal")
    void testWithinEpsilonEqual() {
        assertEquals(new Feet(1.0), new Feet(1.0000000001));
    }

    @Test
    @DisplayName("TC07 - Values beyond EPSILON tolerance should not be equal")
    void testBeyondEpsilonNotEqual() {
        assertNotEquals(new Feet(1.0), new Feet(1.001));
    }

    // ── equals() contract ─────────────────────────────────────────────────────

    @Test
    @DisplayName("TC08 - Reflexivity: a.equals(a)")
    void testReflexivity() {
        Feet a = new Feet(4.0);
        assertEquals(a, a);
    }

    @Test
    @DisplayName("TC09 - Symmetry: a.equals(b) ↔ b.equals(a)")
    void testSymmetry() {
        Feet a = new Feet(6.0), b = new Feet(6.0);
        assertEquals(a, b);
        assertEquals(b, a);
    }

    @Test
    @DisplayName("TC10 - Transitivity: a==b, b==c → a==c")
    void testTransitivity() {
        Feet a = new Feet(7.0), b = new Feet(7.0), c = new Feet(7.0);
        assertEquals(a, b);
        assertEquals(b, c);
        assertEquals(a, c);
    }

    // ── Edge cases ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC11 - Zero should equal zero")
    void testZeroEquality() {
        assertEquals(new Feet(0.0), new Feet(0.0));
    }

    @Test
    @DisplayName("TC12 - Negative value should throw IllegalArgumentException")
    void testNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Feet(-1.0));
    }

    // ── hashCode ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC13 - Equal objects must have equal hashCodes")
    void testHashCodeConsistency() {
        assertEquals(new Feet(5.0).hashCode(), new Feet(5.0).hashCode());
    }
}
