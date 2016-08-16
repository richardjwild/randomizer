package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BooleanRandomizerTest {

    @Test
    public void randomValue() {
        Object actual = Randomizer.forType(Boolean.class).value();
        assertNotNull(actual);
        assertTrue(actual instanceof Boolean);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void lengthNotSupportedForBooleanRandomizer() {
        int someLength = 0;
        Randomizer.forType(Boolean.class).length(someLength);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void maxNotSupportedForBooleanRandomizer() {
        boolean someMax = false;
        Randomizer.forType(Boolean.class).max(someMax);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void minNotSupportedForBooleanRandomizer() {
        boolean someMin = false;
        Randomizer.forType(Boolean.class).min(someMin);
    }
}
