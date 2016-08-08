package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class IntegerRandomizerTest {

    @Test
    public void randomValue() throws Exception {
        Integer value = Randomizer.forType(Integer.class).randomValue();
        assertNotNull(value);
    }

    @Test
    public void randomValueMaximumOneHundred() {
        int maxValue = 100;
        int value = Randomizer.forType(Integer.class).maximum(maxValue).randomValue();
        assertTrue(value <= maxValue);
    }

    @Test
    public void randomValueMinimumTwoBillion() {
        int minValue = 2000000000;
        int value = Randomizer.forType(Integer.class).minimum(minValue).randomValue();
        assertTrue(value >= minValue);
    }

    @Test
    public void randomValueBetweenTwoBounds() {
        int minValue = 5;
        int maxValue = 10;
        int value = Randomizer.forType(Integer.class).minimum(minValue).maximum(maxValue).randomValue();
        assertTrue(minValue <= value);
        assertTrue(value <= maxValue);
    }

    @Test
    public void randomNegativeValue() {
        int minValue = Integer.MIN_VALUE * -1;
        int maxValue = -1;
        int value = Randomizer.forType(Integer.class).minimum(minValue).maximum(maxValue).randomValue();
        assertTrue(minValue <= value);
        assertTrue(value <= maxValue);
    }
}