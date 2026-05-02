package com.qm.quantity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class FeetTest {

    @Test
    void shouldBeEqualWhenSameReference() {
        Feet oneFeet = new Feet(1.0);
        assertEquals(oneFeet, oneFeet);
    }

    @Test
    void shouldBeEqualWhenValuesAreExactlySame() {
        assertEquals(new Feet(2.0), new Feet(2.0));
    }

    @Test
    void shouldBeEqualForFloatingPointPrecisionDifference() {
        assertEquals(new Feet(2.0), new Feet(2.0 + 1e-10));
    }

    @Test
    void shouldNotBeEqualWhenDifferenceIsBeyondTolerance() {
        assertNotEquals(new Feet(2.0), new Feet(2.0 + 1e-4));
    }

    @Test
    void shouldNotBeEqualToNull() {
        assertNotEquals(new Feet(1.0), null);
    }

    @Test
    void shouldNotBeEqualToDifferentType() {
        assertNotEquals(new Feet(1.0), "1.0");
    }

    @Test
    void shouldHaveSameHashCodeForEqualValues() {
        Feet left = new Feet(3.0);
        Feet right = new Feet(3.0 + 1e-10);
        assertEquals(left, right);
        assertEquals(left.hashCode(), right.hashCode());
    }
}
