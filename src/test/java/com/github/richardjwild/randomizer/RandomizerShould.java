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

public class RandomizerShould {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Test
    public void reject_null_type() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Type cannot be null");
        Randomizer.forType(null);
    }

    @Test
    public void reject_unsupported_type() {
        thrown.expect(NoRandomizerFoundException.class);
        thrown.expectMessage("No randomizer found for class: java.lang.Object");
        Randomizer.forType(Object.class);
    }

    @Test
    public void return_a_string_randomizer() {
        Object actual = Randomizer.forType(String.class);
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(StringRandomizer.class);
    }

    @Test
    public void return_an_integer_randomizer() {
        Object actual = Randomizer.forType(Integer.class);
        assertNotNull(actual);
        assertThat(actual).isInstanceOf(IntegerRandomizer.class);
    }

    @Test
    public void return_a_long_randomizer() {
        Object actual = Randomizer.forType(Long.class);
        assertNotNull(actual);
        assertThat(actual).isInstanceOf(LongRandomizer.class);
    }

    @Test
    public void return_a_float_randomizer() {
        Object actual = Randomizer.forType(Float.class);
        assertNotNull(actual);
        assertThat(actual).isInstanceOf(FloatRandomizer.class);
    }

    @Test
    public void return_a_double_randomizer() {
        Object actual = Randomizer.forType(Double.class);
        assertNotNull(actual);
        assertThat(actual).isInstanceOf(DoubleRandomizer.class);
    }

    @Test
    public void return_a_date_randomizer() {
        Object actual = Randomizer.forType(Date.class);
        assertNotNull(actual);
        assertThat(actual).isInstanceOf(DateRandomizer.class);
    }

    @Test
    public void return_a_character_randomizer() {
        Object actual = Randomizer.forType(Character.class);
        assertNotNull(actual);
        assertThat(actual).isInstanceOf(CharacterRandomizer.class);
    }

    @Test
    public void return_a_boolean_randomizer() {
        Object actual = Randomizer.forType(Boolean.class);
        assertNotNull(actual);
        assertThat(actual).isInstanceOf(BooleanRandomizer.class);
    }
}
