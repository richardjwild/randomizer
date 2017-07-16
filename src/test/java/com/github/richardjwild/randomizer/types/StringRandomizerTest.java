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
    public void getValueWithMinLengthZeroThrowsException() {
        Randomizer.forType(String.class).minLength(0).value();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getValueWithMinLengthNegativeThrowsException() {
        Randomizer.forType(String.class).minLength(-1).value();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getValueWithMinLengthGreaterThanMaxLengthThrowsException() {
        Randomizer.forType(String.class).minLength(2).maxLength(1).value();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getValueWithMinCharGreaterThanMaxCharThrowsException() {
        Randomizer.forType(String.class).minChar('b').maxChar('a').value();
    }

    @Test(expected = IllegalArgumentException.class)
    public void lengthAndMaximumLengthMayNotBeSpecifiedAtTheSameTime() {
        Randomizer.forType(String.class).length(1).maxLength(1).value();
    }

    @Test(expected = IllegalArgumentException.class)
    public void lengthAndMinimumLengthMayNotBeSpecifiedAtTheSameTime() {
        Randomizer.forType(String.class).length(1).minLength(1).value();
    }

    @Test
    public void getValueWithLengthSpecified() {
        int length = 100;
        String value = Randomizer.forType(String.class).length(length).value();
        assertNotNull(value);
        assertEquals(length, value.length());
    }

    @Test
    public void getValueWithMaxLengthSpecified() {
        int maxLength = 100;
        String value = Randomizer.forType(String.class).maxLength(maxLength).value();
        assertTrue(value.length() <= maxLength);
    }

    @Test
    public void getValueWithMaxAndMinLengthSpecified() {
        int maxLength = 100;
        int minLength = 90;
        String value = Randomizer.forType(String.class).minLength(minLength).maxLength(maxLength).value();
        assertTrue(minLength <= value.length());
        assertTrue(value.length() <= maxLength);
    }

    @Test
    public void getValueWithMinCharAndMaxCharSpecified() {
        char minChar = 'a';
        char maxChar = 'z';
        String value = Randomizer.forType(String.class).minChar(minChar).maxChar(maxChar).length(100).value();
        for (char c: value.toCharArray()) {
            assertTrue(minChar <= c);
            assertTrue(c <= maxChar);
        }
    }
}