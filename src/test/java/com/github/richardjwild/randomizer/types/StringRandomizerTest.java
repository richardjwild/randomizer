package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Test;

public class StringRandomizerTest {

    @Test(expected = UnsupportedOperationException.class)
    public void maximumMethodNotSupportedForStringRandomizer() {
        String maxValue = "this is meaningless for a string";
        Randomizer.forType(String.class).maximum(maxValue).randomValue();
    }
}