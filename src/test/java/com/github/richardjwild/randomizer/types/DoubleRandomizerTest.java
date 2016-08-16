package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DoubleRandomizerTest {

    @Test
    public void randomValue() {
        Double actual = Randomizer.forType(Double.class).value();
        assertNotNull(actual);
    }


    @Test
    public void randomValueMaximumOneHundred() {
        double maxValue = 100.0D;
        double value = Randomizer.forType(Double.class).max(maxValue).value();
        assertTrue(value <= maxValue);
    }

    @Test
    public void randomValueMinimumTwoBillion() {
        double minValue = 2000000000.0D;
        double value = Randomizer.forType(Double.class).min(minValue).value();
        assertTrue(value >= minValue);
    }

    @Test
    public void randomValueBetweenTwoBounds() {
        double minValue = 5.0D;
        double maxValue = 10.0D;
        double value = Randomizer.forType(Double.class).min(minValue).max(maxValue).value();
        assertTrue(minValue <= value);
        assertTrue(value <= maxValue);
    }

    @Test
    public void randomNegativeValue() {
        double minValue = Double.MAX_VALUE * -1.0D;
        double maxValue = -1;
        double value = Randomizer.forType(Double.class).min(minValue).max(maxValue).value();
        assertTrue(minValue <= value);
        assertTrue(value <= maxValue);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void lengthMethodNotSupportedForDoubleRandomizer() {
        int anyLength = 0;
        Randomizer.forType(Double.class).length(anyLength);
    }
}
