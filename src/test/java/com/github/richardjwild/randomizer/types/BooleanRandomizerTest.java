package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BooleanRandomizerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void randomValue() {
        Object actual = Randomizer.forType(Boolean.class).value();
        assertNotNull(actual);
        assertTrue(actual instanceof Boolean);
    }

    @Test
    public void lengthNotSupportedForBooleanRandomizer() {
        int someLength = 0;
        thrown.expect(UnsupportedOperationException.class);
        Randomizer.forType(Boolean.class).length(someLength);
    }

    @Test
    public void maxNotSupportedForBooleanRandomizer() {
        boolean someMax = false;
        thrown.expect(UnsupportedOperationException.class);
        Randomizer.forType(Boolean.class).max(someMax);
    }

    @Test
    public void minNotSupportedForBooleanRandomizer() {
        boolean someMin = false;
        thrown.expect(UnsupportedOperationException.class);
        Randomizer.forType(Boolean.class).min(someMin);
    }
}
