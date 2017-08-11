package com.github.richardjwild.randomizer;

import com.github.richardjwild.randomizer.types.*;
import com.github.richardjwild.randomizer.validation.NoRandomizerFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;
import java.util.Locale;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

public class RandomizerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Test
    public void throwIllegalArgumentExceptionWhenRandomizerRequestedForNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Type cannot be null");
        Randomizer.forType(null);
    }

    @Test
    public void throwNoRandomizerFoundExceptionWhenRandomizerRequestedForUnsupportedType() {
        thrown.expect(NoRandomizerFoundException.class);
        thrown.expectMessage("No randomizer found for class: java.lang.Object");
        Randomizer.forType(Object.class);
    }

    @Test
    public void getStringRandomizer() {
        Object actual = Randomizer.forType(String.class);
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(StringRandomizer.class);
    }

    @Test
    public void getIntRandomizer() {
        Object actual = Randomizer.forType(Integer.class);
        assertNotNull(actual);
        assertThat(actual).isInstanceOf(IntegerRandomizer.class);
    }

    @Test
    public void getLongRandomizer() {
        Object actual = Randomizer.forType(Long.class);
        assertNotNull(actual);
        assertThat(actual).isInstanceOf(LongRandomizer.class);
    }

    @Test
    public void getFloatRandomizer() {
        Object actual = Randomizer.forType(Float.class);
        assertNotNull(actual);
        assertThat(actual).isInstanceOf(FloatRandomizer.class);
    }

    @Test
    public void getDoubleRandomizer() {
        Object actual = Randomizer.forType(Double.class);
        assertNotNull(actual);
        assertThat(actual).isInstanceOf(DoubleRandomizer.class);
    }

    @Test
    public void getDateRandomizer() {
        Object actual = Randomizer.forType(Date.class);
        assertNotNull(actual);
        assertThat(actual).isInstanceOf(DateRandomizer.class);
    }

    @Test
    public void getCharacterRandomizer() {
        Object actual = Randomizer.forType(Character.class);
        assertNotNull(actual);
        assertThat(actual).isInstanceOf(CharacterRandomizer.class);
    }

    @Test
    public void getBooleanRandomizer() {
        Object actual = Randomizer.forType(Boolean.class);
        assertNotNull(actual);
        assertThat(actual).isInstanceOf(BooleanRandomizer.class);
    }
}
