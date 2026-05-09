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

    // ─────────────────────────────────────────────────────────────────────────
    // 1. Constructor Variants   [Added: 01-May-2026]
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC01 - Two-arg constructor stores feet and inches correctly")
    void testTwoArgConstructor() {
        // 01-May-2026: Verify encapsulation — fields are set and readable via accessors
        Length l = new Length(2.0, 6.0);
        assertEquals(2.0, l.getFeet(),   0.0, "Feet component mismatch");
        assertEquals(6.0, l.getInches(), 0.0, "Inches component mismatch");
    }

    @Test
    @DisplayName("TC02 - One-arg constructor defaults inches to zero")
    void testOneArgConstructorDefaultsInches() {
        // 01-May-2026: Convenience constructor — inches must default to 0.0
        Length l = new Length(3.0);
        assertEquals(0.0, l.getInches(), 0.0, "Inches should default to 0.0");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 2. Basic Equality   [Added: 01-May-2026]
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC03 - Same feet and same inches should be equal")
    void testSameFeetAndInchesEqual() {
        // 01-May-2026: Core encapsulation test — same components → equal objects
        assertEquals(new Length(1.0, 6.0), new Length(1.0, 6.0),
            "Length(1.0, 6.0) should equal Length(1.0, 6.0)");
    }

    @Test
    @DisplayName("TC04 - Different feet with same inches should not be equal")
    void testDifferentFeetNotEqual() {
        // 01-May-2026: Feet component drives inequality
        assertNotEquals(new Length(1.0, 0.0), new Length(2.0, 0.0),
            "1 ft 0 in should not equal 2 ft 0 in");
    }

    @Test
    @DisplayName("TC05 - Same feet with different inches should not be equal")
    void testSameFeetDifferentInchesNotEqual() {
        // 02-May-2026: Inches component also drives inequality
        assertNotEquals(new Length(1.0, 3.0), new Length(1.0, 6.0),
            "1 ft 3 in should not equal 1 ft 6 in");
    }

    @Test
    @DisplayName("TC06 - Length(feet) should equal Length(feet, 0.0)")
    void testSingleArgEqualsZeroInches() {
        // 02-May-2026: Both constructors must produce equivalent objects
        assertEquals(new Length(2.0), new Length(2.0, 0.0),
            "One-arg Length(2.0) must equal two-arg Length(2.0, 0.0)");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 3. Null & Type Safety   [Added: 02-May-2026]
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC07 - Comparison with null should return false")
    void testCompareWithNull() {
        // 02-May-2026: Null guard prevents NullPointerException in equals()
        assertNotEquals(new Length(1.0, 6.0), null,
            "Length should not equal null");
    }

    @Test
    @DisplayName("TC08 - Comparison with a String should return false")
    void testCompareWithWrongType() {
        // 02-May-2026: Type guard — Length("1.0 ft 6.0 in") must be false
        assertNotEquals(new Length(1.0, 6.0), "1.0 ft 6.0 in",
            "Length should not equal a String");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 4. equals() Contract   [Added: 03-May-2026]
    // Reflexive · Symmetric · Transitive — Java Object.equals() contract
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC09 - Reflexivity: a.equals(a) must be true")
    void testReflexivity() {
        // 03-May-2026: Every object must equal itself
        Length a = new Length(3.0, 9.0);
        assertEquals(a, a, "Reflexivity violated");
    }

    @Test
    @DisplayName("TC10 - Symmetry: a.equals(b) implies b.equals(a)")
    void testSymmetry() {
        // 03-May-2026: Equality must not be directional
        Length a = new Length(4.0, 3.0);
        Length b = new Length(4.0, 3.0);
        assertEquals(a, b, "a.equals(b) failed");
        assertEquals(b, a, "Symmetry violated: b.equals(a) failed");
    }

    @Test
    @DisplayName("TC11 - Transitivity: a==b and b==c implies a==c")
    void testTransitivity() {
        // 03-May-2026: Chain of equality must hold
        Length a = new Length(5.0, 0.0);
        Length b = new Length(5.0, 0.0);
        Length c = new Length(5.0, 0.0);
        assertEquals(a, b);
        assertEquals(b, c);
        assertEquals(a, c, "Transitivity violated");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 5. Validation   [Added: 05-May-2026]
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC12 - Negative feet should throw IllegalArgumentException")
    void testNegativeFeetThrows() {
        // 05-May-2026: Physical measurement cannot have negative feet
        assertThrows(IllegalArgumentException.class,
            () -> new Length(-1.0, 0.0),
            "Negative feet must throw IllegalArgumentException");
    }

    @Test
    @DisplayName("TC13 - Inches >= 12 should throw IllegalArgumentException")
    void testInvalidInchesThrows() {
        // 05-May-2026: 12 inches = 1 foot — caller must carry over to feet
        assertThrows(IllegalArgumentException.class,
            () -> new Length(1.0, 12.0),
            "Inches >= 12 must throw IllegalArgumentException");
    }

    @Test
    @DisplayName("TC14 - Negative inches should throw IllegalArgumentException")
    void testNegativeInchesThrows() {
        // 05-May-2026: Negative inches makes no physical sense
        assertThrows(IllegalArgumentException.class,
            () -> new Length(1.0, -1.0),
            "Negative inches must throw IllegalArgumentException");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 6. hashCode Consistency   [Added: 07-May-2026]
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC15 - Equal Length objects must produce equal hashCodes")
    void testHashCodeConsistency() {
        // 07-May-2026: Java contract — a.equals(b) → a.hashCode() == b.hashCode()
        Length a = new Length(2.0, 6.0);
        Length b = new Length(2.0, 6.0);
        assertEquals(a.hashCode(), b.hashCode(),
            "Equal Length objects must have equal hashCodes");
    }
}
