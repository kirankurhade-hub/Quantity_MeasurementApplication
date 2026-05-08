package com.qm.uc01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for UC01 - Feet Measurement Equality.
 *
 * Test coverage:
 *  1. Same value equality
 *  2. Different value inequality
 *  3. Null comparison
 *  4. Wrong type comparison
 *  5. Floating-point precision
 *  6. Reflexivity  (a == a)
 *  7. Symmetry     (a == b ↔ b == a)
 *  8. Transitivity (a==b, b==c → a==c)
 *  9. Negative value validation
 * 10. Zero equality
 */
@DisplayName("UC01 - Feet Measurement Equality")
class FeetTest {

    // ─────────────────────────────────────────────
    // 1. Basic equality
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("Two Feet objects with the same value should be equal")
    void testEqualFeetValues() {
        Feet a = new Feet(5.0);
        Feet b = new Feet(5.0);
        assertEquals(a, b, "5.0 ft == 5.0 ft");
    }

    @Test
    @DisplayName("Two Feet objects with different values should not be equal")
    void testInequalFeetValues() {
        Feet a = new Feet(3.0);
        Feet b = new Feet(5.0);
        assertNotEquals(a, b, "3.0 ft != 5.0 ft");
    }

    // ─────────────────────────────────────────────
    // 2. Null checking
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("Comparing a Feet object with null should return false")
    void testCompareWithNull() {
        Feet a = new Feet(5.0);
        assertNotEquals(a, null, "5.0 ft != null");
    }

    // ─────────────────────────────────────────────
    // 3. Type checking
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("Comparing a Feet object with a different type should return false")
    void testCompareWithDifferentType() {
        Feet a = new Feet(5.0);
        String s = "5.0";
        assertNotEquals(a, s, "Feet should not equal a String");
    }

    @Test
    @DisplayName("Comparing a Feet object with a plain Double should return false")
    void testCompareWithDouble() {
        Feet a = new Feet(5.0);
        Double d = 5.0;
        assertNotEquals(a, d, "Feet should not equal a raw Double");
    }

    // ─────────────────────────────────────────────
    // 4. Floating-point precision
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("Feet values differing by less than epsilon should be equal")
    void testFloatingPointEpsilonEquality() {
        Feet a = new Feet(1.0);
        Feet b = new Feet(1.0000000001); // within epsilon
        assertEquals(a, b, "Values within epsilon tolerance should be equal");
    }

    @Test
    @DisplayName("Feet values differing by more than epsilon should not be equal")
    void testFloatingPointBeyondEpsilon() {
        Feet a = new Feet(1.0);
        Feet b = new Feet(1.001); // outside epsilon
        assertNotEquals(a, b, "Values outside epsilon tolerance should not be equal");
    }

    // ─────────────────────────────────────────────
    // 5. Equality contract
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("Reflexivity: a.equals(a) must be true")
    void testReflexivity() {
        Feet a = new Feet(4.0);
        assertEquals(a, a, "Reflexivity violated");
    }

    @Test
    @DisplayName("Symmetry: a.equals(b) == b.equals(a)")
    void testSymmetry() {
        Feet a = new Feet(6.0);
        Feet b = new Feet(6.0);
        assertEquals(a, b);
        assertEquals(b, a, "Symmetry violated");
    }

    @Test
    @DisplayName("Transitivity: a==b and b==c implies a==c")
    void testTransitivity() {
        Feet a = new Feet(7.0);
        Feet b = new Feet(7.0);
        Feet c = new Feet(7.0);
        assertEquals(a, b);
        assertEquals(b, c);
        assertEquals(a, c, "Transitivity violated");
    }

    // ─────────────────────────────────────────────
    // 6. Edge cases
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("Zero feet should be equal to zero feet")
    void testZeroEquality() {
        Feet a = new Feet(0.0);
        Feet b = new Feet(0.0);
        assertEquals(a, b, "0.0 ft == 0.0 ft");
    }

    @Test
    @DisplayName("Negative value should throw IllegalArgumentException")
    void testNegativeValueThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Feet(-1.0),
                "Negative feet should throw IllegalArgumentException");
    }

    // ─────────────────────────────────────────────
    // 7. hashCode consistency
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("Equal Feet objects must have the same hashCode")
    void testHashCodeConsistency() {
        Feet a = new Feet(5.0);
        Feet b = new Feet(5.0);
        assertEquals(a.hashCode(), b.hashCode(),
                "Equal objects must have equal hashCodes");
    }

    // ─────────────────────────────────────────────
    // 8. toString
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("toString should include the value and 'ft' suffix")
    void testToString() {
        Feet a = new Feet(3.5);
        assertTrue(a.toString().contains("3.5") && a.toString().contains("ft"),
                "toString should be '3.5 ft'");
    }
}
