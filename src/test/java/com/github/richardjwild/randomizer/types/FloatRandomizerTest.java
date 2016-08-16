package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FloatRandomizerTest {

    @Test
    public void randomValue() {
        Float actual = Randomizer.forType(Float.class).value();
        assertNotNull(actual);
    }

    @Test
    public void randomValueMaximumOneHundred() {
        float maxValue = 100.0f;
        float value = Randomizer.forType(Float.class).max(maxValue).value();
        assertTrue(value <= maxValue);
    }

    @Test
    public void randomValueMinimumTwoBillion() {
        float minValue = 2000000000.0f;
        float value = Randomizer.forType(Float.class).min(minValue).value();
        assertTrue(value >= minValue);
    }

    @Test
    public void randomValueBetweenTwoBounds() {
        float minValue = 5.0f;
        float maxValue = 10.0f;
        float value = Randomizer.forType(Float.class).min(minValue).max(maxValue).value();
        assertTrue(minValue <= value);
        assertTrue(value <= maxValue);
    }

    @Test
    public void randomNegativeValue() {
        float minValue = Float.MAX_VALUE * -1.0f;
        float maxValue = -1;
        float value = Randomizer.forType(Float.class).min(minValue).max(maxValue).value();
        assertTrue(minValue <= value);
        assertTrue(value <= maxValue);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void lengthMethodNotSupportedForFloatRandomizer() {
        int anyLength = 0;
        Randomizer.forType(Float.class).length(anyLength);
    }
}
