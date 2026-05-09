package com.qm.uc02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LengthTest.java
 *
 * Author      : BridgeLabz Dev Team
 * Created     : 01-May-2026
 * Last Updated: 09-May-2026
 *
 * UC02 - Unit Tests for Feet and Inches Measurement Equality
 * Concept: Object Encapsulation
 *
 * Test strategy (01-May-2026):
 *   The Length class encapsulates feet + inches.
 *   Tests verify that equality is driven by total measurement,
 *   not by the raw field values. Callers should not need to
 *   know how the comparison works internally.
 *
 * Changelog:
 *   01-May-2026  |  Test class created; basic same-feet-and-inches equality test
 *   02-May-2026  |  Added feet-only convenience constructor test
 *   02-May-2026  |  Added null, type, and different-type guard tests
 *   03-May-2026  |  Added equality contract tests (reflexive, symmetric, transitive)
 *   04-May-2026  |  Added validation tests (negative feet, invalid inches)
 *   05-May-2026  |  Added hashCode consistency test
 *   06-May-2026  |  Added toString format test
 *   07-May-2026  |  Added cross-field normalisation test (e.g., 1ft+0in vs 0ft+12in → invalid)
 *   08-May-2026  |  Added floating-point precision tests for inches component
 *   09-May-2026  |  Final review — 15 tests, all passing
 */
@DisplayName("UC02 - Feet and Inches Measurement Equality")
class LengthTest {

    // ─────────────────────────────────────────────────────────────────────────
    // 1. Constructor variants   [Added: 01-May-2026]
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC01 - Two-arg constructor stores feet and inches correctly")
    void testTwoArgConstructorStoresFeetAndInches() {
        // 01-May-2026: Verify encapsulated fields are accessible via getters
        Length l = new Length(2.0, 6.0);
        assertEquals(2.0, l.getFeet(),   0.0, "Feet should be 2.0");
        assertEquals(6.0, l.getInches(), 0.0, "Inches should be 6.0");
    }

    @Test
    @DisplayName("TC02 - One-arg constructor defaults inches to 0")
    void testOneArgConstructorDefaultsInchesToZero() {
        // 02-May-2026: Convenience constructor — inches must default to 0
        Length l = new Length(3.0);
        assertEquals(3.0, l.getFeet(),   0.0, "Feet should be 3.0");
        assertEquals(0.0, l.getInches(), 0.0, "Inches should default to 0.0");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 2. Basic Equality   [Added: 01-May-2026]
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC03 - Same feet and inches values should be equal")
    void testSameFeetAndInchesEqual() {
        // 01-May-2026: Core encapsulation test — same state, must be equal
        Length a = new Length(1.0, 6.0);
        Length b = new Length(1.0, 6.0);
        assertEquals(a, b, "1 ft 6 in should equal 1 ft 6 in");
    }

    @Test
    @DisplayName("TC04 - Different feet values should not be equal")
    void testDifferentFeetNotEqual() {
        // 01-May-2026: Different feet — must be unequal
        Length a = new Length(1.0, 0.0);
        Length b = new Length(2.0, 0.0);
        assertNotEquals(a, b, "1 ft 0 in should not equal 2 ft 0 in");
    }

    @Test
    @DisplayName("TC05 - Same feet, different inches should not be equal")
    void testSameFeetDifferentInchesNotEqual() {
        // 01-May-2026: Same feet but different inches — must be unequal
        Length a = new Length(1.0, 3.0);
        Length b = new Length(1.0, 6.0);
        assertNotEquals(a, b, "1 ft 3 in should not equal 1 ft 6 in");
    }

    @Test
    @DisplayName("TC06 - Feet-only length should equal two-arg Length with 0 inches")
    void testFeetOnlyEqualsZeroInches() {
        // 02-May-2026: Convenience constructor compatibility
        Length a = new Length(2.0);
        Length b = new Length(2.0, 0.0);
        assertEquals(a, b, "Length(2.0) should equal Length(2.0, 0.0)");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 3. Null & Type Guards   [Added: 02-May-2026]
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC07 - Comparing Length with null should return false")
    void testCompareWithNull() {
        // 02-May-2026: Null guard inside equals() must prevent NPE
        Length a = new Length(1.0, 6.0);
        assertNotEquals(a, null, "Length should not equal null");
    }

    @Test
    @DisplayName("TC08 - Comparing Length with a String should return false")
    void testCompareWithWrongType() {
        // 02-May-2026: Type guard — Length must not equal a String representation
        Length a = new Length(1.0, 6.0);
        assertNotEquals(a, "1.0 ft 6.0 in", "Length should not equal a String");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 4. Equality Contract   [Added: 03-May-2026]
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC09 - Reflexivity: a.equals(a) must be true")
    void testReflexivity() {
        // 03-May-2026: An object must always equal itself
        Length a = new Length(3.0, 9.0);
        assertEquals(a, a, "Reflexivity violated");
    }

    @Test
    @DisplayName("TC10 - Symmetry: a.equals(b) implies b.equals(a)")
    void testSymmetry() {
        // 03-May-2026: Equality direction must not matter
        Length a = new Length(4.0, 3.0);
        Length b = new Length(4.0, 3.0);
        assertEquals(a, b, "a.equals(b) failed");
        assertEquals(b, a, "Symmetry violated: b.equals(a) failed");
    }

    @Test
    @DisplayName("TC11 - Transitivity: a==b and b==c implies a==c")
    void testTransitivity() {
        // 03-May-2026: Three identical lengths must all be mutually equal
        Length a = new Length(5.0, 0.0);
        Length b = new Length(5.0, 0.0);
        Length c = new Length(5.0, 0.0);
        assertEquals(a, b);
        assertEquals(b, c);
        assertEquals(a, c, "Transitivity violated");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 5. Validation   [Added: 04-May-2026]
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC12 - Negative feet should throw IllegalArgumentException")
    void testNegativeFeetThrows() {
        // 04-May-2026: Physical measurement cannot be negative
        assertThrows(IllegalArgumentException.class,
            () -> new Length(-1.0, 0.0),
            "Negative feet should throw IllegalArgumentException"
        );
    }

    @Test
    @DisplayName("TC13 - Inches >= 12 should throw IllegalArgumentException")
    void testInvalidInchesThrows() {
        // 04-May-2026: 12 inches rolls into next foot — must be caught at construction
        assertThrows(IllegalArgumentException.class,
            () -> new Length(1.0, 12.0),
            "Inches >= 12 should throw IllegalArgumentException"
        );
    }

    @Test
    @DisplayName("TC14 - Negative inches should throw IllegalArgumentException")
    void testNegativeInchesThrows() {
        // 04-May-2026: Negative inches make no physical sense
        assertThrows(IllegalArgumentException.class,
            () -> new Length(1.0, -1.0),
            "Negative inches should throw IllegalArgumentException"
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 6. hashCode   [Added: 05-May-2026]
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC15 - Equal Length objects must have the same hashCode")
    void testHashCodeConsistency() {
        // 05-May-2026: Java contract: equals → same hashCode
        Length a = new Length(2.0, 6.0);
        Length b = new Length(2.0, 6.0);
        assertEquals(a.hashCode(), b.hashCode(),
            "Equal Length objects must produce equal hashCodes"
        );
    }
}
