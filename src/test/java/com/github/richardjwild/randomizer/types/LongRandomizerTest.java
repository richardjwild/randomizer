package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LongRandomizerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void randomValue() {
        Long actual = Randomizer.forType(Long.class).value();
        assertNotNull(actual);
    }

    @Test
    public void randomValueMaximumOneHundred() {
        long maxValue = 100;
        long value = Randomizer.forType(Long.class).max(maxValue).value();
        assertTrue(value <= maxValue);
    }

    @Test
    public void randomValueMinimumTwoBillion() {
        long minValue = 2000000000;
        long value = Randomizer.forType(Long.class).min(minValue).value();
        assertTrue(value >= minValue);
    }

    @Test
    public void randomValueBetweenTwoBounds() {
        long minValue = 5;
        long maxValue = 10;
        long value = Randomizer.forType(Long.class).min(minValue).max(maxValue).value();
        assertTrue(minValue <= value);
        assertTrue(value <= maxValue);
    }

    @Test
    public void randomNegativeValue() {
        long minValue = Integer.MIN_VALUE;
        long maxValue = -1;
        long value = Randomizer.forType(Long.class).min(minValue).max(maxValue).value();
        assertTrue(minValue <= value);
        assertTrue(value <= maxValue);
    }

    @Test
    public void lengthMethodNotSupportedForIntegerRandomizer() {
        int anyLength = 0;
        thrown.expect(UnsupportedOperationException.class);
        Randomizer.forType(Long.class).length(anyLength);
    }
}
