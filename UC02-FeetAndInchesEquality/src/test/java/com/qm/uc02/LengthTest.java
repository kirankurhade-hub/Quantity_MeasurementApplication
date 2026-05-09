package com.qm.uc02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LengthTest.java
 *
 * Author      : Kiran Kurhade
 * Created     : 01-May-2026
 * Last Updated: 09-May-2026
 *
 * UC02 - Unit Tests for Feet and Inches Measurement Equality
 * Concept: Object Encapsulation
 *
 * Purpose (01-May-2026):
 *   Verify that the Length class properly encapsulates feet + inches.
 *   Tests confirm callers interact only through the public API —
 *   they never manipulate raw fields or call toTotalInches() directly.
 *
 * Changelog:
 *   01-May-2026  |  Test class created; TC01–TC06 (constructor + basic equality)
 *   02-May-2026  |  Added TC07–TC08: null and wrong-type guards
 *   03-May-2026  |  Added TC09–TC11: equals() contract (reflexive, symmetric, transitive)
 *   05-May-2026  |  Added TC12–TC14: constructor validation (negative feet/inches, >=12)
 *   07-May-2026  |  Added TC15: hashCode consistency
 *   09-May-2026  |  Final @DisplayName review; all 15 tests passing
 */
@DisplayName("UC02 - Feet and Inches Measurement Equality")
class LengthTest {

    // ── Constructor variants ──────────────────────────────────────────────────

    @Test
    @DisplayName("TC01 - Two-arg constructor stores feet and inches")
    void testTwoArgConstructor() {
        Length l = new Length(2.0, 6.0);
        assertEquals(2.0, l.getFeet(),   0.0);
        assertEquals(6.0, l.getInches(), 0.0);
    }

    @Test
    @DisplayName("TC02 - One-arg constructor defaults inches to 0")
    void testOneArgConstructorDefaultsInches() {
        Length l = new Length(3.0);
        assertEquals(0.0, l.getInches(), 0.0);
    }

    // ── Basic equality ────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC03 - Same feet and inches should be equal")
    void testSameFeetAndInchesEqual() {
        assertEquals(new Length(1.0, 6.0), new Length(1.0, 6.0));
    }

    @Test
    @DisplayName("TC04 - Different feet should not be equal")
    void testDifferentFeetNotEqual() {
        assertNotEquals(new Length(1.0, 0.0), new Length(2.0, 0.0));
    }

    @Test
    @DisplayName("TC05 - Same feet, different inches should not be equal")
    void testSameFeetDifferentInchesNotEqual() {
        assertNotEquals(new Length(1.0, 3.0), new Length(1.0, 6.0));
    }

    @Test
    @DisplayName("TC06 - Length(feet) should equal Length(feet, 0)")
    void testSingleArgEqualsZeroInches() {
        assertEquals(new Length(2.0), new Length(2.0, 0.0));
    }

    // ── Null & type safety ────────────────────────────────────────────────────

    @Test
    @DisplayName("TC07 - Comparison with null should return false")
    void testCompareWithNull() {
        assertNotEquals(new Length(1.0, 6.0), null);
    }

    @Test
    @DisplayName("TC08 - Comparison with a String should return false")
    void testCompareWithWrongType() {
        assertNotEquals(new Length(1.0, 6.0), "1.0 ft 6.0 in");
    }

    // ── equals() contract ─────────────────────────────────────────────────────

    @Test
    @DisplayName("TC09 - Reflexivity: a.equals(a)")
    void testReflexivity() {
        Length a = new Length(3.0, 9.0);
        assertEquals(a, a);
    }

    @Test
    @DisplayName("TC10 - Symmetry: a.equals(b) ↔ b.equals(a)")
    void testSymmetry() {
        Length a = new Length(4.0, 3.0), b = new Length(4.0, 3.0);
        assertEquals(a, b);
        assertEquals(b, a);
    }

    @Test
    @DisplayName("TC11 - Transitivity: a==b, b==c → a==c")
    void testTransitivity() {
        Length a = new Length(5.0, 0.0),
               b = new Length(5.0, 0.0),
               c = new Length(5.0, 0.0);
        assertEquals(a, b);
        assertEquals(b, c);
        assertEquals(a, c);
    }

    // ── Validation ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC12 - Negative feet should throw IllegalArgumentException")
    void testNegativeFeetThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Length(-1.0, 0.0));
    }

    @Test
    @DisplayName("TC13 - Inches >= 12 should throw IllegalArgumentException")
    void testInvalidInchesThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Length(1.0, 12.0));
    }

    @Test
    @DisplayName("TC14 - Negative inches should throw IllegalArgumentException")
    void testNegativeInchesThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Length(1.0, -1.0));
    }

    // ── hashCode ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC15 - Equal Length objects must have equal hashCodes")
    void testHashCodeConsistency() {
        assertEquals(new Length(2.0, 6.0).hashCode(), new Length(2.0, 6.0).hashCode());
    }
}
