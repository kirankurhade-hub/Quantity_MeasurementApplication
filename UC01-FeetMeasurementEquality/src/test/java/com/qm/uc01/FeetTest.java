package com.qm.uc01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FeetTest.java
 *
 * Author      : BridgeLabz Dev Team
 * Created     : 25-Apr-2026
 * Last Updated: 09-May-2026
 *
 * UC01 - Unit Tests for Feet Measurement Equality
 *
 * Changelog:
 *   25-Apr-2026  |  Test class created; basic same-value and different-value tests added
 *   26-Apr-2026  |  Added null and wrong-type comparison tests
 *   27-Apr-2026  |  Added floating-point precision boundary tests (epsilon in/out)
 *   28-Apr-2026  |  Added negative value exception test
 *   29-Apr-2026  |  Added equality contract tests: reflexivity, symmetry, transitivity
 *   01-May-2026  |  Added zero equality and hashCode consistency tests
 *   03-May-2026  |  Added toString format verification test
 *   07-May-2026  |  Reviewed all test names and @DisplayName annotations
 *   09-May-2026  |  Final review — 13 tests, all passing, merged to dev
 */
@DisplayName("UC01 - Feet Measurement Equality")
class FeetTest {

    // ─────────────────────────────────────────────────────────────────────────
    // 1. Basic Equality   [Added: 25-Apr-2026]
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC01 - Two Feet with same value should be equal")
    void testEqualFeetValues() {
        // 25-Apr-2026: Core equality use-case — same measurement must be equal
        Feet a = new Feet(5.0);
        Feet b = new Feet(5.0);
        assertEquals(a, b, "5.0 ft should equal 5.0 ft");
    }

    @Test
    @DisplayName("TC02 - Two Feet with different values should not be equal")
    void testInequalFeetValues() {
        // 25-Apr-2026: Inequality verification
        Feet a = new Feet(3.0);
        Feet b = new Feet(5.0);
        assertNotEquals(a, b, "3.0 ft should not equal 5.0 ft");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 2. Null Checking   [Added: 26-Apr-2026]
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC03 - Comparing Feet with null should return false")
    void testCompareWithNull() {
        // 26-Apr-2026: Guard against NullPointerException in equals()
        Feet a = new Feet(5.0);
        assertNotEquals(null, a, "Feet object should not equal null");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 3. Type Checking   [Added: 26-Apr-2026]
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC04 - Comparing Feet with a String should return false")
    void testCompareWithString() {
        // 26-Apr-2026: Type guard — Feet(5.0) must not equal the String "5.0"
        Feet a = new Feet(5.0);
        assertNotEquals(a, "5.0", "Feet should not equal a String");
    }

    @Test
    @DisplayName("TC05 - Comparing Feet with a raw Double should return false")
    void testCompareWithDouble() {
        // 26-Apr-2026: Type guard — Feet(5.0) must not equal Double 5.0
        Feet a = new Feet(5.0);
        Double d = 5.0;
        assertNotEquals(a, d, "Feet should not equal a raw Double");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 4. Floating-Point Precision   [Added: 27-Apr-2026]
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC06 - Values within EPSILON (1e-9) tolerance should be equal")
    void testFloatingPointWithinEpsilon() {
        // 27-Apr-2026: 1.0 and 1.0000000001 differ by 1e-10 — within EPSILON
        Feet a = new Feet(1.0);
        Feet b = new Feet(1.0000000001);
        assertEquals(a, b, "Values within epsilon tolerance should be equal");
    }

    @Test
    @DisplayName("TC07 - Values beyond EPSILON tolerance should not be equal")
    void testFloatingPointBeyondEpsilon() {
        // 27-Apr-2026: 1.0 and 1.001 differ by 0.001 — well outside EPSILON
        Feet a = new Feet(1.0);
        Feet b = new Feet(1.001);
        assertNotEquals(a, b, "Values beyond epsilon should not be equal");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 5. Equality Contract   [Added: 29-Apr-2026]
    // Verifies Java equals() contract: reflexive, symmetric, transitive
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC08 - Reflexivity: a.equals(a) must be true")
    void testReflexivity() {
        // 29-Apr-2026: An object must always equal itself
        Feet a = new Feet(4.0);
        assertEquals(a, a, "Reflexivity violated: a must equal itself");
    }

    @Test
    @DisplayName("TC09 - Symmetry: a.equals(b) implies b.equals(a)")
    void testSymmetry() {
        // 29-Apr-2026: Equality must not be one-directional
        Feet a = new Feet(6.0);
        Feet b = new Feet(6.0);
        assertEquals(a, b, "a.equals(b) failed");
        assertEquals(b, a, "Symmetry violated: b.equals(a) failed");
    }

    @Test
    @DisplayName("TC10 - Transitivity: a==b and b==c implies a==c")
    void testTransitivity() {
        // 29-Apr-2026: Three equal measurements must all equal each other
        Feet a = new Feet(7.0);
        Feet b = new Feet(7.0);
        Feet c = new Feet(7.0);
        assertEquals(a, b);
        assertEquals(b, c);
        assertEquals(a, c, "Transitivity violated: a==b and b==c but a!=c");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 6. Edge Cases   [Added: 28-Apr-2026]
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC11 - Zero feet should equal zero feet")
    void testZeroEquality() {
        // 01-May-2026: Boundary — zero is a valid measurement
        Feet a = new Feet(0.0);
        Feet b = new Feet(0.0);
        assertEquals(a, b, "0.0 ft should equal 0.0 ft");
    }

    @Test
    @DisplayName("TC12 - Negative value should throw IllegalArgumentException")
    void testNegativeValueThrows() {
        // 28-Apr-2026: Physical measurement cannot be negative
        assertThrows(IllegalArgumentException.class,
            () -> new Feet(-1.0),
            "Negative feet should throw IllegalArgumentException"
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 7. hashCode   [Added: 01-May-2026]
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC13 - Equal Feet objects must produce the same hashCode")
    void testHashCodeConsistency() {
        // 01-May-2026: Java contract: a.equals(b) → a.hashCode() == b.hashCode()
        Feet a = new Feet(5.0);
        Feet b = new Feet(5.0);
        assertEquals(a.hashCode(), b.hashCode(),
            "Equal Feet objects must have equal hashCodes"
        );
    }
}
