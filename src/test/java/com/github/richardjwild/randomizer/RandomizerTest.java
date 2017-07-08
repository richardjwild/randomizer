package com.github.richardjwild.randomizer;

import com.github.richardjwild.randomizer.exception.NoRandomizerFoundException;
import com.github.richardjwild.randomizer.types.*;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class RandomizerTest {

    @Test(expected = NoRandomizerFoundException.class)
    public void throwNoRandomizerFoundExceptionWhenRandomizerRequestedForUnsupportedType() {
        Randomizer.forType(Object.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionWhenRandomizerRequestedForNull() {
        Randomizer.forType(null);
    }

    @Test
    public void exceptionHasCorrectMessageWhenRandomizerRequestedForUnsupportedType() {
        try {
            Randomizer.forType(Object.class);
        } catch (NoRandomizerFoundException actual) {
            String expected = String.format("No randomizer found for class: java.lang.Object");
            assertEquals(expected, actual.getMessage());
        }
    }

    @Test
    public void getStringRandomizer() {
        Object actual = Randomizer.forType(String.class);
        assertNotNull(actual);
        assertSame(StringRandomizer.class, actual.getClass());
    }

    @Test
    public void getIntRandomizer() {
        Object actual = Randomizer.forType(Integer.class);
        assertNotNull(actual);
        assertSame(IntegerRandomizer.class, actual.getClass());
    }

    @Test
    public void getLongRandomizer() {
        Object actual = Randomizer.forType(Long.class);
        assertNotNull(actual);
        assertSame(LongRandomizer.class, actual.getClass());
    }

    @Test
    public void getFloatRandomizer() {
        Object actual = Randomizer.forType(Float.class);
        assertNotNull(actual);
        assertSame(FloatRandomizer.class, actual.getClass());
    }

    @Test
    public void getDoubleRandomizer() {
        Object actual = Randomizer.forType(Double.class);
        assertNotNull(actual);
        assertSame(DoubleRandomizer.class, actual.getClass());
    }

    @Test
    public void getDateRandomizer() {
        Object actual = Randomizer.forType(Date.class);
        assertNotNull(actual);
        assertSame(DateRandomizer.class, actual.getClass());
    }

    @Test
    public void getCharacterRandomizer() {
        Object actual = Randomizer.forType(Character.class);
        assertNotNull(actual);
        assertSame(CharacterRandomizer.class, actual.getClass());
    }

    @Test
    public void getBooleanRandomizer() {
        Object actual = Randomizer.forType(Boolean.class);
        assertNotNull(actual);
        assertSame(BooleanRandomizer.class, actual.getClass());
    }
}
