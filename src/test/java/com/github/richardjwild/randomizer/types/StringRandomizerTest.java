package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StringRandomizerTest {

    @Test(expected = UnsupportedOperationException.class)
    public void maximumMethodNotSupportedForStringRandomizer() {
        String maxValue = "this is not supported for a string";
        Randomizer.forType(String.class).max(maxValue).value();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void minimumMethodNotSupportedForStringRandomizer() {
        String minValue = "this is not supported for a string";
        Randomizer.forType(String.class).min(minValue).value();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getValueWithoutSpecifyingLengthThrowsException() {
        Randomizer.forType(String.class).value();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getValueWithLengthZeroThrowsException() {
        Randomizer.forType(String.class).length(0).value();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getValueWithLengthNegativeThrowsException() {
        Randomizer.forType(String.class).length(-1).value();
    }

    @Test
    public void getValueWithLengthSpecified() {
        int length = 100;
        Object value = Randomizer.forType(String.class).length(length).value();
        assertNotNull(value);
        assertTrue(value instanceof String);
        assertEquals(length, value.toString().length());
    }
}