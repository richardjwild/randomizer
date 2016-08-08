package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Test;

import static org.junit.Assert.*;

public class IntegerRandomizerTest {

    @Test
    public void randomValue() throws Exception {
        int value = Randomizer.forType(Integer.class).randomValue();
        System.out.println(value);
    }

    @Test
    public void randomValueMaximumOneHundred() {
        int maxValue = 100;
        int value = Randomizer.forType(Integer.class).maximum(maxValue).randomValue();
        assertTrue(value <= maxValue);
    }
}