package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Test;

import static org.junit.Assert.*;

public class IntegerRandomizerTest {

    @Test
    public void randomValue() throws Exception {
        Integer value = Randomizer.forType(Integer.class).value();
        assertNotNull(value);
    }

    @Test
    public void randomValueMaximumOneHundred() {
        int maxValue = 100;
        int value = Randomizer.forType(Integer.class).max(maxValue).value();
        assertTrue(value <= maxValue);
    }

    @Test
    public void randomValueMinimumTwoBillion() {
        int minValue = 2000000000;
        int value = Randomizer.forType(Integer.class).min(minValue).value();
        assertTrue(value >= minValue);
    }

    @Test
    public void randomValueBetweenTwoBounds() {
        int minValue = 5;
        int maxValue = 10;
        int value = Randomizer.forType(Integer.class).min(minValue).max(maxValue).value();
        assertTrue(minValue <= value);
        assertTrue(value <= maxValue);
    }

    @Test
    public void randomNegativeValue() {
        int minValue = Integer.MIN_VALUE * -1;
        int maxValue = -1;
        int value = Randomizer.forType(Integer.class).min(minValue).max(maxValue).value();
        assertTrue(minValue <= value);
        assertTrue(value <= maxValue);
    }
}