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
    public void getValueWithoutSpecifyingLengthOrMaxLengthThrowsException() {
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

    @Test(expected = IllegalArgumentException.class)
    public void getValueWithMaxLengthZeroThrowsException() {
        Randomizer.forType(String.class).maxLength(0).value();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getValueWithMaxLengthNegativeThrowsException() {
        Randomizer.forType(String.class).maxLength(-1).value();
    }

    @Test(expected = IllegalArgumentException.class)
    public void lengthAndMaximumLengthMayNotBeSpecifiedAtTheSameTime() {
        Randomizer.forType(String.class).length(1).maxLength(1).value();
    }

    @Test
    public void getValueWithLengthSpecified() {
        int length = 100;
        Object value = Randomizer.forType(String.class).length(length).value();
        assertNotNull(value);
        assertTrue(value instanceof String);
        assertEquals(length, value.toString().length());
    }

    @Test
    public void getValueWithMaxLengthSpecified() {
        int maxLength = 100;
        String value = Randomizer.forType(String.class).maxLength(maxLength).value();
        assertTrue(value.length() <= maxLength);
    }
}