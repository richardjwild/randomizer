package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CharacterRandomizerTest {

    @Test
    public void randomValue() {
        Character value = Randomizer.forType(Character.class).value();
        assertNotNull(value);
    }

    @Test
    public void randomValueGreaterThanMinimum() {
        char minimum = 'A';
        char actual = Randomizer.forType(Character.class).min(minimum).value();
        assertTrue(actual >= minimum);
    }

    @Test
    public void randomValueSmallerThanMaximum() {
        char maximum = 'z';
        char actual = Randomizer.forType(Character.class).max(maximum).value();
        assertTrue(actual <= maximum);
    }

    @Test
    public void randomValueBetweenTwoBounds() {
        char minimum = 'A';
        char maximum = 'z';
        char actual = Randomizer.forType(Character.class).min(minimum).max(maximum).value();
        assertTrue(minimum <= actual);
        assertTrue(actual <= maximum);
    }
}
