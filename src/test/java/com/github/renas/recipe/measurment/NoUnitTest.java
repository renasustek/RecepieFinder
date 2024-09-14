package com.github.renas.recipe.measurment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NoUnitTest {

    @Test
    void testValue() {
        double expected = 1.0;
        NoUnit noUnit = new NoUnit(1.0);
        assertEquals(expected, noUnit.value());
    }

    @Test
    void testUnit() {
        double expected = 1.0;
        NoUnit noUnit = new NoUnit(1.0);
        assertEquals(Unit.NOUNIT, noUnit.unit());
    }
}
